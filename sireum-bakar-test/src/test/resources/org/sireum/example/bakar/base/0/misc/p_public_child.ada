package P
--# own Y, B, C;  -- Y is public, B is private (declared in body)
--# initializes Y, B, C;
is
  Y: Integer;
  procedure Q(X: in Integer; Z: out Integer);
  --# global in B;
  --# derives Z from X, B;
private
   C: Integer;
end P;

package body P is

   B: Integer;
  --# inherit P;
  package P2
  is
    procedure R(X: out Integer);
    --# global in P.Y, P.B, P.C;
    --# derives X from P.Y, P.B, P.C;
  end P2;

  -- NOTE: Child packages may be declared only at library level
  --       so the following is illegal in SPARK
  -- package P2.P3 is
  --   procedure D(X: in out Integer);
  --     --# derives X from X;
  -- end P2.P3;
  
  -- package body P2.P3 is
  --   procedure D(X: in out Integer) is
  --   begin
  --       X := X + 1;
  --   end D;
  -- end P2.P3;
        
  package body P2 is
  
      package P3 is
        procedure D(X: in out Integer);
          --# derives X from X;
      end P3;

      package body P3 is
        procedure D(X: in out Integer) is
          begin
            X := X + 1;
          end D;
      end P3;
      
    procedure R(X: out Integer) 
    is
      -- NOTE: this is valid spark but the compiler doesn't currently
      --       emit the correct pilar code for packages embedded 
      --       within procedures (it will think P3's parent is P2 and
      --       not R).  This package should only be visible within R
      --package P3 is
      --  procedure D(X: in out Integer);
      --    --# derives X from X;
      --end P3;

      --package body P3 is
      --  procedure D(X: in out Integer) is
      --    begin
      --      X := X + 1;
      --    end D;
      --end P3;
      
    begin
      X := (P.Y + P.B) + P.C;
    end R;
  end P2;


    
  procedure Q(X: in Integer; Z: out Integer)
  is
  begin
    Z := X + B;
  end Q;

begin
   Y := 1;
   B := 0;
   C := 0;
end P;

--# inherit P;
package P.P2
--# own Q;
--# initializes Q;
is
  Q: Integer;
  procedure Q2(X: in Integer; Y: out Integer; Z: out Integer);
  --# global in P.Y, P.C;
  --# derives Y from X &
  --#         Z from P.Y, P.C;

end P.P2;

package body P.P2 is

  package P3 is
    procedure D(X: in out Integer);
      --# derives X from X;
    end P3;

  package body P3 is
    procedure D(X: in out Integer) is
      begin
        X := X + 1;
      end D;
  end P3;
      
  procedure Q2(X: in Integer; Y: out Integer; Z: out Integer) is
  begin
     Y := X;
     Z := P.Y + P.C;
  end Q2;

begin
  Q := 3;
end P.P2;

--#inherit P.P2;
package P.P2.P3 is
  procedure D(X: in out Integer);
  --# derives X from X;
end P.P2.P3;

package body P.P2.P3 is
  procedure D(X: in out Integer) is
  begin
    X := X + 1;
  end D;
end P.P2.P3;
