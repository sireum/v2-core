package Value_Present_Pkg
is
   subtype Index is Integer range 1 .. 10;
   type AType is array (Index) of Integer;

   function Value_Present (A: AType; X : Integer) return Boolean;
   --# return for some M in Index => (A(M) = X);

end Value_Present_Pkg;

package body Value_Present_Pkg
is
   function Value_Present (A: AType; X : Integer) return Boolean
   is
      Result : Boolean;
   begin
      Result := False;
      for I in Index loop
         if A(I) = X then
            Result := True;
            exit;
         end if;
         --# assert I in Index and
         --#        not Result and
         --#        (for all M in Index range Index'First .. I => (A(M) /= X));
      end loop;
      return Result;
   end Value_Present;

end Value_Present_Pkg;
