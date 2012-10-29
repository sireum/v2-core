-- Definitions of base model types used by NeoSpark (SpAda).
--
-- @author Patrice Chalin, SAnToS Lab & DSRG.org.

package Model is
   subtype Z is Integer; -- the mathematical integers

   -- Model Type for sets over the mathematical integers.

   --# type AZ_Set is abstract;
   type Z_Set is array(Z) of Boolean;
   
   function Z_Set_Empty return Z_Set;
   function Z_Set_Add(e: Z; s: Z_Set) return Z_Set;
   function Z_Set_Remove(e: Z; s: Z_Set) return Z_Set;
   function Z_Set_Size(s: Z_Set) return Integer;
   function Z_Set_IsEmpty(s: Z_Set) return Boolean;
   function Z_Set_IsMember(e: Z; s: Z_Set) return Boolean;

   -- function isMember(E: Z; S: Z_Set) return Boolean;
   -- function isEmpty(S: Z_Set) return Boolean;
   -- function Size(S: Z_Set) return Natural;

   procedure Add(E: in Z; S: in out Z_Set);
   --# derives S from E, S;
   
   procedure Empty(S : out Z_Set);
   --# derives S from;

end Model;
