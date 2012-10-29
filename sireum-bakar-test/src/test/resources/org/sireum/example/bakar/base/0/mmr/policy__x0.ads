with Lbl_t;
--# inherit Lbl_t;
package Policy
  --# own Policy_Space;
  --# initializes Policy_Space;
is
  type Policy_Row is array (Lbl_t.Proc_Id) of Boolean;
  type Policy_Matrix is array (Lbl_t.Proc_Id) of Policy_Row;
  Policy_Space: Policy_Matrix;
  
  function Is_Allowed(Origin: in Lbl_t.Proc_Id; Dest: in Lbl_t.Proc_Id)
                      return Boolean;
    --# global in Policy_Space;
  
  -- we need this function temporarily to constrain the policy matrix to
  -- the defined policy
  function Is_Satisfied
                      return Boolean;
    --# global in Policy_Space;
 end Policy;
