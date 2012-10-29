-- Overview: The router consists of two components: a virusCheck module and
--	     an encryption module. The virusCheck module will forward the message 
--	     it receives if no virus is found, otherwise it will log the message into Z. 
--           The encryption module will check 
--	     the classification tag on the message and encrypt it if it is classified information.
--           The output Y flows into public domain and Z flows into classified domain.
package sec_classification

	is
	procedure virusCheck(X: in Integer; M: out Integer; Z: out Integer);
	--# derives M from X
	--#           & Z from X;

	procedure encryption(M: in Integer; Y: out Integer);
	--# derives Y from M;

	procedure Router(X: in Integer; Y: out Integer; Z: out Integer);
	--# derives Z,Y from X;
	end sec_classification;

package body sec_classification
is
	procedure virusCheck(X: in Integer; M: out Integer; Z: out Integer)
	is
		begin
     		if X > 0
     		then
       			M := X;
       			Z := 0;
     		else
       			Z := X;
       			M := 0;
       		end if;
	end virusCheck;
	
	procedure encryption(M: in Integer; Y: out Integer) --message M is classified and Y public
	is
		begin
      		if M rem 2 = 0
      		then
      		<<class>> --Y here is classified 
			Y := M + 1;
     		else
     		<<noclass>> --Y here is public 
			Y := M;
		end if;
	end encryption;

	procedure Router(X: in Integer; Y: out Integer; Z: out Integer)
	is
		M : Integer;
		begin
		
		-- derives M, Z from X;
		virusCheck(X, M, Z);
	
		-- derives Y from M
		encryption(M,Y);
	end Router;
	
end sec_classification;

