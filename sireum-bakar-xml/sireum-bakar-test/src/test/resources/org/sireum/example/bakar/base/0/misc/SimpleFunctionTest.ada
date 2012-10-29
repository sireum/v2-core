-- with Instruments, Degrees, Surfaces;
-- inherit Instruments, Degrees, Surfaces;

package FunctionTest is
   subtype Scaledata is Integer range -100..100;
   
   function Scale_Movement(
         Mach : Integer;
         Present : in Scaledata;
         Target  : in Scaledata;
         Max     : in Integer)
        return Integer;
   --# pre (Max > 0);
   --# return M => ( (-Max <= M) and (M <= Max) );
end FunctionTest;