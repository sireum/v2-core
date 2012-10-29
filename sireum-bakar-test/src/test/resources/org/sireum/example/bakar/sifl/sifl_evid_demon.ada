package SIFLEvidTest
is
   procedure SIFLEvidTest_Example (q: in Integer; t: out Integer);
     --# derives t from q;

end SIFLEvidTest;

package body SIFLEvidTest is

   procedure SIFLEvidTest_Example (q: in Integer; t: out Integer)
   is
   r : Integer;
   begin
      r := q;
      t := r;
   end SIFLEvidTest_Example;
end SIFLEvidTest;