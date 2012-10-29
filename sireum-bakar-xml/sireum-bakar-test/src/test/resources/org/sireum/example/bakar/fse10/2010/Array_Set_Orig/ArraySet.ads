with ArraySetDefs;
--# inherit ArraySetDefs,
--#         ArraySetUnsigned;
package ArraySet
--# own State;

is
   --------------------------------------------------------------------------
   --  NAME: Get_Value
   --
   --  DESCRIPTION:
   --  Returns the Value entry for the given ID.  The Value information is
   --  not valid unless Found is True.
   --------------------------------------------------------------------------

   procedure Get_Value
     (ID : in ArraySetDefs.ID_Type;
      Value    :  out ArraySetDefs.Value_Type;
      Value_Length :  out ArraySetDefs.Value_Length_Type;
      Found  : out Boolean);
   --# global in State;
   --# derives Value, Value_Length, Found from ID, State;



   --------------------------------------------------------------------------
   --  NAME: Add
   --
   --  DESCRIPTION:
   --  Adds a Value entry for the given ID. If the ID is null or if an
   --  entry for the ID already exists, sets an error Response.
   --------------------------------------------------------------------------

   procedure Add
     (ID           : in ArraySetDefs.ID_Type;
      Value        : in ArraySetDefs.Value_Type;
      Value_Length : in ArraySetDefs.Value_Length_Type;
      Response   : out ArraySetDefs.Response_Type);
   --# global in out State;
   --# derives  State from
   --#            ID, Value, Value_Length, State &
   --# Response from  ID, State ;


   --------------------------------------------------------------------------
   --  NAME: Delete
   --
   --  DESCRIPTION:
   --  Deletes the Value entry for the given ID.
   --------------------------------------------------------------------------
   procedure Delete
     (ID       : in ArraySetDefs.ID_Type;
      Response : out ArraySetDefs.Response_Type);
   --# global in out State;
   --# derives Response, State from
   --#            ID, State;

   --------------------------------------------------------------------------
   --  NAME: Init
   --
   --  DESCRIPTION:
   --  Initializes the set of Values.
   --
   --  LIMITATIONS:
   --  Must be called prior to any other operations.
   --------------------------------------------------------------------------
   procedure Init;
   --# global out State;
   --# derives State from ;

end ArraySet;
