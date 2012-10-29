package A is
  procedure Q(X: in Integer; Z: out Integer);
  --# derives Z from X;
end A;

package body A is
  procedure Q(X: in Integer; Z: out Integer) is
  begin
     Z := X + 2;
  end Q;
end A;
