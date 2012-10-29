package Max2Int
is
   function Max (X, Y : Integer) return Integer;
   --# return M => (X > Y -> M = X) and
   --#             (Y > X -> M = Y);
end Max2Int;

package body Max2Int
is
   function Max (X, Y : Integer) return Integer
   is
      Result : Integer;
   begin
      if X > Y then
         Result := X;
      else
         Result := Y;
      end if;
      return Result;
   end Max;
end Max2Int;
