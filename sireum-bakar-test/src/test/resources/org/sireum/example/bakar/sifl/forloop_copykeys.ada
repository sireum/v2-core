package ForLoop_CopyKeys
is
  type Index is range 0..9;
  type KeyTable is array (Index) of Integer;
  
  --Inkeys: KeyTable := KeyTable'(others=>1);
  procedure CopyKeys(Inkeys : in KeyTable; Outkeys: in out KeyTable; K: in Index; V: out Integer);
  --# derives Outkeys from Inkeys, Outkeys &
  --#         V       from K, Inkeys, Outkeys;
  
  
--  procedure CopyKeyTable(X : in KeyTable;
--			 Y : out KeyTable);
--  --# derives Y from X;

end ForLoop_CopyKeys;

package body ForLoop_CopyKeys
is
   
  procedure CopyKeys(Inkeys : in KeyTable; Outkeys: in out KeyTable; K: in Index; V: out Integer)
  is
  begin
     for I in Index range 0 .. 9 loop
       Outkeys(I) := Inkeys(I);
     end loop;
     V := Outkeys(K);
  end CopyKeys;
   

end ForLoop_CopyKeys;
