with A;
--# inherit A;
package B is
  procedure T(M: in Integer; Z: out Integer);
  --# derives Z from M;
end B;


package body B is
  procedure T(M: in Integer; Z: out Integer) is
  begin
    A.Q(M, Z);
  end T;
end B;