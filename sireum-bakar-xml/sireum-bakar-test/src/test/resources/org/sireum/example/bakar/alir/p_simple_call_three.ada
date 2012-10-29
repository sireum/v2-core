package p_simple_call_three
        --# own AbsOwn, G, H;
        --# initializes AbsOwn, G, H;
        
        is
        procedure R0(A: in Integer; B: out Integer);
        --# global in G, H; in out AbsOwn;
        --# derives B from AbsOwn
        --#       & AbsOwn from A, G, H;
        
        procedure R1(Q: in Integer; R: in out Integer);
        --# global in H; out G; in out AbsOwn;
        --# derives R from AbsOwn, R
        --#       & G from H, Q
        --#       & AbsOwn from AbsOwn, Q;
        -- The need to have AbsOwn from AbsOwn, A; instead of just
        -- AbsOwn from A; is pretty tricky.  It seems to be the case that
        -- if AbsOwn is both inported and exported, and in the refinement
        -- not all of its
        -- constituents are exported (in this case X and Y),
        -- then there is an implicit dependency on itself.  That is,
        -- since X and Y are not changed (they may not even be used in
        -- the procedure, e.g., X is not used in R1) their final value
        -- depends on their initial value, thus the abstraction AbsOwns should
        -- depend on itself.  This means that inferring an abstract derives
        -- clause may need to consider constituents of an abstraction that
        -- are not used at all.
        
        procedure Q1(A: in Integer; B: in out Integer; C: out Integer);
        --# global in H; out G; in out AbsOwn;
        --# derives B from AbsOwn, B
        --#       & C from AbsOwn, H, B
        --#       & G from H, A
        --#       & AbsOwn from AbsOwn, A;
        
        private
        G: Integer := 0;
        H: Integer := 0;
	end p_simple_call_three;
        
	package body p_simple_call_three
        --# own AbsOwn is X,Y,Z;
        is
        X: Integer;
        Y: Integer;
        Z: Integer;
        
        procedure R0(A: in Integer; B: out Integer)
        --# global in G, H; out X, Z; in out Y;
        --# derives B from Y
        --#       & X from H
        --#       & Y from G
        --#       & Z from A;
        is
            begin
            B := Y;
            X := H;
            Y := G;
            Z := A;
            end R0;
        
        procedure R1(Q: in Integer; R: in out Integer)
        --# global in H, Y; out G, Z;
        --# derives R from Y, R
        --#       & G from H, Q
        --#       & Z from Q;
        is
            begin
            R := Y + R;
            G := H + Q;
	<<AtZ>>
            Z := Q;
            end R1;
        
        procedure Q1(A: in Integer; B: in out Integer; C: out Integer)
        --# global in H, X, Y; out G, Z;
        --# derives B from Y, B
        --#       & C from X, Y, H, B
        --#       & G from H, A
        --#       & Z from A;
      
        is
        begin
	<<test>>
            C := X + Y;
            --A := X + 1;
            R1(A,B);
            C := C + H + B;
        end Q1;
        
        
        begin
        X := 1;
        Y := 0;
        Z := 0;
	end p_simple_call_three;
        
