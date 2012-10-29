
package body inter_package_dependency
 is
 procedure foo(A: in out Integer)

      is 
        begin
        inter_provider.bar;
        A := A + inter_provider.C;
        A := A + 1;
   end foo;
end inter_package_dependency;