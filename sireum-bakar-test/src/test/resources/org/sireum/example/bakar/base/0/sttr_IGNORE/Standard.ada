-- Chalin: reasonable definitions for standard types. These help
-- in conducting proofs -- e.g. so that one doesn't have to prove
-- that 0 in Integer.
--
-- cf. Barnes Spark book p. 196.

package Standard is
  type Integer is range -2**31 .. 2**31-1;
end Standard;