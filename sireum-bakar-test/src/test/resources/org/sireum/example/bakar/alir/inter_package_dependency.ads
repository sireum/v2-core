with inter_provider;
--#inherit inter_provider;

package inter_package_dependency
   is
 procedure foo(A: in out Integer);
  --# global in inter_provider.A, inter_provider.B; out inter_provider.C  ;
  --# derives A from A, inter_provider.A,inter_provider.B &
  --#         inter_provider.C from inter_provider.A,inter_provider.B  ;
end inter_package_dependency;