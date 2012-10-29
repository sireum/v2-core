-- SPARK does not allow ragged arrays

package Array_Two_Dim
  --# own Box, Box2, aBoard;
  --# initializes Box, Box2, aBoard;
is
  constTwo: constant := 2;
  constThree: constant := constTwo + 1; -- can Pilar handle this??
  
  highInt: constant := 10;
  subtype Index is Integer range 1..highInt;
  subtype subIndexRange is Index range 6..8;
  
  -- two dim unconstrained array, cannot use this directly, must subtype it
  type Matrix is array (Integer range <>, Integer range <>) of Integer;
  
  -- constraining subtype
  subtype Square is Matrix (Index, Index);

  Box: Square;
  
  -- declaration and initialization of a multidim array.  Inner aggregate 
  -- cannot be qualified as they are not objects of a named types in their own 
  -- right (Barnes 5.6)
  Box2: Square := Square'(others => (others => 0));
  
  -- array of arrays
  type Board_Index is range 1..8;
  type Row is array (Board_Index) of Integer;
  type Board is array (Board_Index) of Row;

  -- declaration and initialization of an array of arrays.  Inner aggregates
  -- have to be qualified 
  aBoard: Board := Board'(others => Row'(others => 1));
        
  procedure dummy(X : in Index; Y: in Index);
  --# global in out Box;
  --# derives Box from Box, X, Y;

  procedure dummy2(M : in Board_Index; N: in Board_Index);
  --# global in out aBoard;
  --# derives aBoard from aBoard, M, N;
   
end Array_Two_Dim;

package body Array_Two_Dim is
   procedure dummy(X : in Index; Y: in Index) is
   begin
      Box(X, Y) := Box(X, Y) - 1;
   end dummy;

  procedure dummy2(M: in Board_Index; N: in Board_Index) is
  begin
    aBoard(M)(N) := aBoard(M)(N) - 1;
  end dummy2;
  
begin 
   Box := Square'(-- RangeChoice
                  1..constThree => (Index => 0, others => 1),

                  -- SubTypeChoice
                  Index range constThree + 1 .. 5 => (1..3 => 1, others => 2), 

                  -- SubTypeChoice (though parser will see this as an ExpChoice                  
                  subIndexRange => (others => 3),
                  
                  -- ExpChoice
                  10 - 2 + 1 => (others => 5),
                  
                  -- default
                  others => (others => 3));
end Array_Two_Dim;