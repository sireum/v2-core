package body Policy
is
  function Is_Allowed(Origin: in Lbl_t.Proc_Id; Dest: in Lbl_t.Proc_Id)
                      return Boolean is
  begin
    return Policy_Space(Origin)(Dest);
  end Is_Allowed;
  
   -- we need this function temporarily to constrain the policy matrix to
  -- the defined policy
  function Is_Satisfied return Boolean is
  begin
    return
    (Policy_Space(1)(1) = False and then
     Policy_Space(1)(2) = True  and then
     Policy_Space(1)(3) = False and then
     Policy_Space(2)(1) = False and then
     Policy_Space(2)(2) = False and then
     Policy_Space(2)(3) = True  and then
     Policy_Space(3)(1) = False and then
     Policy_Space(3)(2) = True  and then
     Policy_Space(3)(3) = False);
  end Is_Satisfied;
  
  begin
    -- here we define which processes can communicate.  The
    --  following policy is the same one that was presented in
    --  the paper
    Policy_Space := Policy_Matrix'(others =>
                                   (Policy_Row'(others => False)));
    Policy_Space(1)(2) := True;
    Policy_Space(2)(3) := True;
    Policy_Space(3)(2) := True;
--    Policy_Space(3)(5) := True;
--    Policy_Space(4)(1) := True;
--    Policy_Space(5)(3) := True;
--    Policy_Space(5)(4) := True;
--    Policy_Space(5)(6) := True;
--    Policy_Space(6)(4) := True;
end Policy;
      
