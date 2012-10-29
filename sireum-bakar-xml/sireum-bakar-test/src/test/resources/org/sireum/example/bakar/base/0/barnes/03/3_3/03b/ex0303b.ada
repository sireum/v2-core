package P is
   function Max(I,J : Integer) return Integer;
end P;

package body P is
   function Max(I,J : Integer) return Integer is
      Result : Integer;
   begin
      if I > J then
         Result := I;
      else
         Result := J;
      end if;
      return Result;
   end Max;
end P;

