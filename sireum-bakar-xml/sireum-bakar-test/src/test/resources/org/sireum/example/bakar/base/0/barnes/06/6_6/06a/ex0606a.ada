-- Example of tagged types
package P is
   type Object is tagged
      record
         X_Coord, Y_Coord : Float;
      end record;

   procedure Init_Obj(X, Y : Float; Obj : out Object);
   --# derives Obj from X, Y;

   function Distance(Obj : Object) return Float;
   -- distance from origin

   function Area(Obj : Object) return Float;
end P;

with P;
--# inherit P;
package Q is
   type Circle is new P.Object with
      record
         Radius : Float;
      end record;

   procedure Init_Circle(X, Y, R : Float; C : out Circle);
   --# derives C from X, Y, R;

   function Area(C : Circle) return Float;
end Q;


