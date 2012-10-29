package body Test_Case_10 is
    
    procedure P1 (I1, I2, I3 : in Integer; O1, O2 : out Integer)
    --# derives O1 from I1, I2
    --# & O2 from I3;
    is
        L1, L2 : Integer;
    begin
        L2 := 5;
        L1 := I3;
        O2 := L1;
        L1 := I2; -- kill I3
        L1 := I1 + I2;
        if L1 > 5
        then
            if I1 < 0
            then
                L2 := 8;
            else
                L2 := 7;
            end if;
        end if;
        O1 := L2;
    end P1;
end Test_Case_10;
