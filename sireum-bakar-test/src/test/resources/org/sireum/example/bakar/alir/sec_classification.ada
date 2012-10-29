-- This example is created to check some information flow properties
-- and to check information flow policies.
-- Overview: This program consists of 4 sectors represented as 4 procedures
--	     and a Router. Each sector performs some maniulation of the data 
--	     it receives and return it back. The router calles these sectors 
--	     so we can analysis the information flows between each sectors.
package sec_classification

	is
	procedure Sector1(A1: in Integer; B1: out Integer);
	--# derives B1 from A1;

	procedure Sector2(A2: in out Integer);
	--# derives A2 from A2;

	procedure Sector3(A3: in Integer; B3: in Integer; C3: out Integer);
	--# derives C3 from A3, B3;

	procedure Sector4(A4: in Integer; B4: out Integer; C4: in out Integer);
	--# derives B4 from A4, C4
	--#       & C4 from C4, A4;

	procedure Router(X: in Integer; Y: in out Integer; Z: in out Integer);
	--# derives Y from X, Z, Y
	--#	  & Z from X, Z;
	end sec_classification;

	package body sec_classification
	is
	procedure Sector1(A1: in Integer; B1: out Integer)
	is
		begin
		B1 := A1;
		end Sector1;

	procedure Sector2(A2: in out Integer)
	is
		begin
		A2 := A2 + 1;
		end Sector2;

	procedure Sector3(A3: in Integer; B3: in Integer; C3: out Integer)
	is 
		begin
		C3 := A3 + B3;
		end Sector3;

	procedure Sector4(A4: in Integer; B4: out Integer; C4: in out Integer)
	is
		begin
		B4 := A4 + C4;
		C4 := B4 + A4;
		end Sector4;

	procedure Router(X: in Integer; Y: in out Integer; Z: in out Integer)
	is
		M : Integer;
		begin
		M := Y;
		
		-- derives Y from Z;
		Sector1(Z,Y);
		
		M := Y + M;

		-- derives Z from X, Y
		Sector3(X,Y,Z);
		M := M + Y ;

		-- derives Y from Z, Z & Z from Z, X;
		Sector4(X,Y,Z);

		--derives Y from Y
		Sector2(Y);
		Y := Y + M;
		
		end Router;
		
	end sec_classification;

