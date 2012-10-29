with ArraySetDefs;
with ArraySetUnsigned;
use type ArraySetDefs.ID_Type;
use type ArraySetDefs.Value_Type;

package body ArraySet
--# own State is Item_List, Next_List, Free_Head, Used_Head;
is
  Max_Items : constant := 5; -- belt: originally 16

  type Item_Type is record
    ID           : ArraySetDefs.ID_Type;
    Value        : ArraySetDefs.Value_Type;
    Value_Length : ArraySetDefs.Value_Length_Type;
  end record;

  subtype Item_List_Index_Type is ArraySetUnsigned.Word range 0 .. Max_Items - 1;
  subtype Link_Type is ArraySetUnsigned.Word range 0 .. Max_Items;

  type Item_List_Type is array (Item_List_Index_Type) of Item_Type;
  type Next_List_Type is array (Item_List_Index_Type) of Link_Type;

  Item_List : Item_List_Type;
  Next_List : Next_List_Type;

  Terminator : constant := Link_Type'Last;
  Inf_Length : constant := Max_Items + 1;
  Free_Head : Link_Type;
  Used_Head : Link_Type;

  ------------------Invariant

  ---
  --- @param head the index of the start of a list (expected to be either
  ---             Free_Head or Used_Head)
  --- @return the number of elements reachable from head, or Inf_Length
  ---         if the list is cyclic.
  ---
  function Size_Of_List(head : Link_Type) return ArraySetUnsigned.Word
    --# global in Next_List;
  is
    Cursor : Link_Type;
    Result : ArraySetUnsigned.Word := 0;
  begin
    Cursor := head;
    while Cursor /= Terminator and Result < Inf_Length loop
      Result := Result + 1;
      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end Size_Of_List;

  ---
  --- @return True if a cycle is detected
  ---
  function Is_Cyclic(head : Link_Type) return Boolean
    --# global in Next_List;
  is begin
    return size_of_list(head) = Inf_Length;
  end Is_Cyclic;

  ---
  --- combines the used element invariants into a single function
  --- @return True if ...
  ---
  function Used_Elements_Invariant return Boolean
    --# global in Item_List, Next_List, Used_Head;
  is
    --Item : Item_Type;
    Cursor, Cursor2 : Link_Type;
    Result : Boolean := True;
  begin
    Cursor := Used_Head;
    while Result and then Cursor /= Terminator loop
      --Item := Item_List(Cursor);
      --Result := Item.ID /= ArraySetDefs.Null_ID
      --          --belt: array comparison not currently handled
      --          and then Item.Value_Length /= ArraySetDefs.Null_Value_Length;
      --
      -- The above is commented out due to Kiasan not currently using copy
      -- on write semantics for non-scalar types.  
      -- Since the lhs of the first assignment is an actual variable of 
      -- composite type, Kiasan clones the record retrieved from the rhs. 
      -- The materialization of the fields of the record (as well as the 
      -- subsequently generated constraints) made as a result of executing the 
      -- next few lines are placed on the cloned record rather than the record 
      -- stored in Item_List. Therefore the desired pre-state was never achieved
      
      Result := Item_List(Cursor).ID /= ArraySetDefs.Null_ID
                --belt: array comparison not currently handled
                --and then Item_List(Cursor).Value /= ArraySetDefs.Null_Value
                and then Item_List(Cursor).Value_Length /= ArraySetDefs.Null_Value_Length;

      Cursor2 := Next_List(Cursor);

      while Result and then Cursor2 /= Terminator loop
        -- check for uniqueness of used element IDs
        Result := Item_List(Cursor).ID /= Item_List(Cursor2).ID;
        Cursor2 := Next_List(Cursor2);
      end loop;

      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end Used_Elements_Invariant;

  function Free_Elements_Invariant return Boolean
    --# global Item_List, Next_List, Free_Head;
  is
    --Item : Item_Type;
    Cursor : Link_Type;
    Result : Boolean := True;
  begin
    Cursor := Free_Head;
    while Result and then Cursor /= Terminator loop
      Result := Item_List(Cursor).ID = ArraySetDefs.Null_ID
                --belt: array comparison not currently handled
                --and then Item_List(Cursor).Value = ArraySetDefs.Null_Value
                and then Item_List(Cursor).Value_Length = ArraySetDefs.Null_Value_Length;

      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end Free_Elements_Invariant;

  ---
  --- @return True if all invariants hold
  ---
  function Invariant return Boolean
    --# global in Next_List, Free_Head, Used_Head, Item_List;
  is
  begin
    return not Is_Cyclic(Free_Head)
           and then not Is_Cyclic(Used_Head)           
           and then Used_Elements_Invariant
           and then Free_Elements_Invariant

      -- belt: enforcing this invariant would require Kiasan to expand
      --       Next_List and therefore Kiasan would have 16 indices to 
      --       consider.  Possible solution is to reduce Max_Items
      and then Size_Of_List(Free_Head) + Size_Of_List(Used_Head) = Max_Items 
      ;

  end Invariant;

  ---
  --- @return True if ID is found in the used portion of the array
  ---
  function contains(ID : ArraySetDefs.ID_Type) return Boolean
    --# global Used_Head, Item_List, Next_List;
  is
    Result : Boolean := false;
    Cursor : Link_Type;
  begin
    Cursor := Used_Head;
    while not Result and then Cursor /= Terminator loop
      Result := Item_List(Cursor).ID = ID;
      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end contains;

  ---
  --- @param ID the id of the record to look for
  --- @param Value
  --- @param Value_Length
  --- @param Found True if ID was found in the used elements
  ---
  --- @pre ID is not null
  --- @post
  ---      - Value_Length actually corresponds to length of Value
  ---      - Found is true  when ID is in the set;
  ---        Found is false when ID is not in the set;
  ---      - Found is true implies
  ---          Value, Value_Length not null
  ---      - Found is false implies
  ---          Value, Value_Length are null
  ---
  procedure Get_Value
    (ID           : in  ArraySetDefs.ID_Type;
     Value        : out ArraySetDefs.Value_Type;
     Value_Length : out ArraySetDefs.Value_Length_Type;
     Found        : out Boolean)

    --# global in Item_List, Used_Head, Next_List
    --#        , Free_Head                    -- needed for call to Invariant
    --#        ;
    --#
    --# derives Value, Value_Length, Found from
    --#           ID, Used_Head, Item_List, Next_List 
    --#         & null from Free_Head            -- needed for Invariant call 
    --#         ;
    --#
    --# pre ID /= ArraySetDefs.Null_ID
    --#     -- NOTE: currently we have to explicitly call the invariant in
    --#     --       Kiasan.  In a future version this will be implicit
    --#     and then Invariant(Next_List, Free_Head, Used_Head, Item_List)
    --#     ;
    --#
    --# post Invariant(Next_List, Free_Head, Used_Head, Item_List) and then
    --#
    --#      contains(ID, Used_Head, Item_List, Next_List) = Found and then
    --#
    --#      (Found -> (
    --#        --Value /= ArraySetDefs.Null_Value and then   -- array_comparison
    --#        Value_Length /= ArraySetDefs.Null_Value_Length))
    --#
    --#      and then
    --#      (not Found -> (
    --#        --Value = ArraySetDefs.Null_Value and then    -- array_comparison
    --#        Value_Length = ArraySetDefs.Null_Value_Length));
  is
    Curr_Index  : Link_Type;
  begin
    --# accept Flow_Message, 30, Free_Head, "Needed for call to Invariant";
    
    Value := ArraySetDefs.Null_Value;
    Value_Length := ArraySetDefs.Null_Value_Length;
    Curr_Index := Used_Head;
    Found := False;

    while not Found and then Curr_Index /= Terminator loop
      if Item_List (Curr_Index).ID = ID then
        Value := Item_List (Curr_Index).Value;
        Value_Length := Item_List(Curr_Index).Value_Length;
        Found := True;
      else
        Curr_Index := Next_List (Curr_Index);
      end if;
    end loop;

  end Get_Value;

  ---
  --- @pre
  ---      - ID is not null
  ---      - Value is not null
  ---      - Value_Length is not null  check ****and matches length of Value****
  --- @post
  ---       - Response code is "correct"
  ---         Important to deal with cases where the DB is full and thus
  ---         value does not get added.
  ---
  procedure Add
    (ID           : in  ArraySetDefs.ID_Type;
     Value        : in  ArraySetDefs.Value_Type;
     Value_Length : in  ArraySetDefs.Value_Length_Type;
     Response     : out ArraySetDefs.Response_Type)
     
    --# global in out Item_List, Next_List, Free_Head, Used_Head;
    --# derives Item_List from ID, Value, Value_Length, Item_List, Next_List,
    --#           Free_Head, Used_Head &
    --#         Response, Next_List, Free_Head, Used_Head from
    --#           ID, Item_List, Next_List, Free_Head, Used_Head;
    --#
    --# pre (ID /= ArraySetDefs.Null_ID) and then
    --#     --(Value /= ArraySetDefs.Null_Value) and then -- array comparison
    --#     (Value_Length /= ArraySetDefs.Null_Value_Length)
    --#
    --#     and then Invariant(Next_List, Free_Head, Used_Head, Item_List)
    --#     ;
    --#
    --# post Invariant(Next_List, Free_Head, Used_Head, Item_List) and then
    --#
    --#      (Response = ArraySetDefs.DB_No_Room -> 
    --#         (Free_Head~ = Terminator and then Free_Head~ = Free_Head))
    --#
    --#      -- belt: ID may already exist in the set which is full.  The
    --#      --       algorithm first checks if the array is full so the 
    --#      --       response would be ArraySetDefs.DB_No_Room 
    --#      --and then
    --#      --((for all I in Item_List_Index_Type => (ID /= Item_List~(I).ID)) ->
    --#      --  Response = ArraySetDefs.DB_Success)
    --#
    --#      --and then
    --#      --((for some I in Item_List_Index_Type => (ID = Item_List~(I).ID)) =
    --#      --    (Response = ArraySetDefs.DB_Already_Exists))
    --#
    --#      and then
    --#      (Response = ArraySetDefs.DB_Success -> (
    --#       (for all I in Item_List_Index_Type => (ID /= Item_List~(I).ID))
    --#       and then
    --#       (for some I in Item_List_Index_Type => (ID = Item_List(I).ID))
    --#      ))
    --#    
    --#      and then
    --#      (Response = ArraySetDefs.DB_Already_Exists -> (
    --#        for some I in Item_List_Index_Type => (
    --#          ID = Item_List~(I).ID and then ID = Item_List(I).ID)))
    --#      ;
  is
    Curr_Index : Link_Type;
    Temp_Value : ArraySetDefs.Value_Type;
    Temp_Value_Length : ArraySetDefs.Value_Length_Type;
    Found : Boolean;
  begin
    if ID /= ArraySetDefs.Null_ID then

      -- Check for full used list
      if Free_Head /= Terminator then

        -- Ensure the ID is not already in the list
        --# accept Flow_Message, 10, Temp_Value, " Assignment to Temp_Value  is ineffective";
        --# accept Flow_Message, 10, Temp_Value_Length, " Assignment to Temp_Value_Length  is ineffective";
        Get_Value(ID, Temp_Value, Temp_Value_Length, Found);
        --# end accept;

        if not Found then
          -- Use the entry at the head of the free list
          Curr_Index := Free_Head;

          -- Remove this entry from the free list
          Free_Head := Next_List (Free_Head);

          -- Fill in the values
          Item_List (Curr_Index).ID := ID;
          Item_List (Curr_Index).Value :=  Value;
          Item_List (Curr_Index).Value_Length := Value_Length;

          -- Insert this entry into the used list
          Next_List (Curr_Index) := Used_Head;
          Used_Head              := Curr_Index;

          Response := ArraySetDefs.DB_Success;

        else
          Response := ArraySetDefs.DB_Already_Exists;
        end if;

      else
        Response := ArraySetDefs.DB_No_Room;
      end if;

    else
      Response := ArraySetDefs.DB_Input_Check_Fail;
    end if;

    --# accept Flow_Message, 33, Temp_Value, "The variable Temp_Value is neither referenced nor exported.";
    --# accept Flow_Message, 33, Temp_Value_Length, "The variable Temp_Value_Length is neither referenced nor exported.";

  end Add;

  ---
  --- @pre ID is not null
  --- @post Response code is "correct"
  ---
  procedure Delete
    (ID          : in  ArraySetDefs.ID_Type;
     Response    : out ArraySetDefs.Response_Type)

    --# global in out Item_List, Next_List, Free_Head, Used_Head;
    --#
    --# derives Next_List, Free_Head from
    --#           ID, Item_List, Next_List, Free_Head, Used_Head &
    --#         Response, Used_Head, Item_List from
    --#           ID, Item_List, Next_List, Used_Head ;
    --#
    --# pre (ID /= ArraySetDefs.Null_ID)
    --#     and then Invariant(Next_List, Free_Head, Used_Head, Item_List)
    --#     ;
    --#
    --# post Invariant(Next_List, Free_Head, Used_Head, Item_List)
    --#
    --#      and then
    --#      (Response = ArraySetDefs.DB_Does_Not_Exist ->
    --#        contains(ID, Used_Head~, Item_List~, Next_List~) = False)
    --#        --(for all I in Item_List_Index_Type => (ID /= Item_List~(I).ID)))    
    --#
    --#      and then
    --#       --((for all I in Item_List_Index_Type => (ID /= Item_List~(I).ID)) ->    
    --#      (contains(ID, Used_Head~, Item_List~, Next_List~) = False ->
    --#        Response = ArraySetDefs.DB_Does_Not_Exist)
    --#    
    --#      and then
    --#      (Response = ArraySetDefs.DB_Success ->
    --#         (contains(ID, Used_Head~, Item_List~, Next_List~) = true
    --#
    --#          and then 
    --#          contains(ID, Used_Head, Item_List, Next_List) = false))
    --#    
    --#         --(for some I in Item_List_Index_Type =>
    --#         --  (Item_List~(I).ID = ID
    --#         --   and then Item_List(I).ID = ArraySetDefs.Null_ID
    --#         --   --and then Item_List(I).Value = ArraySetDefs.Null_Value -- array comparison
    --#         --   and then Item_List(I).Value_Length = ArraySetDefs.Null_Value_Length)))
    --#      ;
  is
    Curr_Index : Link_Type;
    Prev_Index : Link_Type;
  begin

    Prev_Index := Terminator;
    Curr_Index := Used_Head;

    while Curr_Index /= Terminator and then Item_List (Curr_Index).ID /= ID loop
      Prev_Index := Curr_Index;
      Curr_Index := Next_List (Curr_Index);
    end loop;

    if Curr_Index /= Terminator then

      -- Not necessary but may help debug - impacts SPARK annotations
      -- belt: this is needed when the package invariant is included
      Item_List (Curr_Index).ID := ArraySetDefs.Null_ID;
      Item_List (Curr_Index).Value := ArraySetDefs.Null_Value;
      Item_List (Curr_Index).Value_Length := ArraySetDefs.Null_Value_Length;

      -- Update the used list to remove the deleted entry
      if Prev_Index = Terminator then
        Used_Head := Next_List (Curr_Index);
      else
        Next_List (Prev_Index) := Next_List (Curr_Index);
      end if;

      -- Move deleted entry to the head of the free list
      Next_List (Curr_Index) := Free_Head;
      Free_Head := Curr_Index;

      Response := ArraySetDefs.DB_Success;
    else
      Response := ArraySetDefs.DB_Does_Not_Exist;
    end if;

  end Delete;

  procedure Init
    --# global out Item_List, Next_List, Free_Head, Used_Head;
    --# derives Item_List, Next_List, Free_Head, Used_Head from ;
    --# post Invariant(Next_List, Free_Head, Used_Head, Item_List);
  is
  begin
    for I in Item_List_Index_Type loop

      --# accept Flow_Message, 23, Item_List, "undefined array element(s)";
      -- Note: this error has to be accepted because SPARK cannot tell
      -- that all array values are being initialized.  This is something
      -- that the KSU array information flow analysis is designed to
      -- overcome.
      Item_List (I).ID := ArraySetDefs.Null_ID;
      Item_List (I).Value := ArraySetDefs.Null_Value;
      Item_List (I).Value_Length := ArraySetDefs.Null_Value_Length;

      --# end accept;
    end loop;

    for I in Link_Type
      range 0 .. Link_Type'Last - 1 loop

      --# accept Flow_Message, 23, Next_List, "undefined array element(s)";
      Next_List (I) := I + 1;
      --# end accept;
    end loop;

    Next_List (Link_Type'Last-1) := Terminator;
    Free_Head := Link_Type'First;
    Used_Head := Terminator;

    --# accept Flow_Message, 602, Item_List, Item_List, "undefined array element(s)";
    --# accept Flow_Message, 602, Next_List, Next_List, "undefined array element(s)";
    --  SPARK has a problem with initializing individual entries in
    --  an array or aggregate. Using deferred constants is prohibitive
    --  for runtime as code generated by the compiler is huge.
  end Init;

end ArraySet;
