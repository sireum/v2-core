
package body Router
        is

    procedure Audit(AuditCode: in Integer; Src: in RouterComm.PartitionID; Dst: in RouterComm.PartitionID; AuditMsg: in out RouterComm.MsgType)
	is 
	begin
		AuditMsg.Source := Src;
		AuditMsg.Destination := Dst;
		AuditMsg.Payload(Dst) := AuditCode;
	end Audit;

    procedure ApplyGuardPolicy(Policy: in RouterComm.PolicyType; Src: in RouterComm.PartitionID; OutMsg: in out RouterComm.MsgType; Result : out Integer)
    	is 
    	begin
		Result := 0; 
    		if Policy(src) /= true then
    			OutMsg := RouterComm.NullMsg;
    			Result := 3; -- Error code when GuardPolicy fails
    		end if;
    		
    	end ApplyGuardPolicy;

    procedure ProcessOnePartition(Partition: in RouterComm.PartitionID; OutBox: in out RouterComm.BufferType; CommPolicy: in RouterComm.CommPolicyType; GuardPolicy: in RouterComm.GuardPolicyType)
            -- Process one partition
                is
		Result : Integer;
            begin
        
            -- If the partition does not have a message to be sent to audit, create an empty audit message and put it in the audit outbox cell
            -- Alternatively, we could make the Audit() procedure to check whether the AuditMsg is null.
	  
            If OutBox(RouterComm.AuditPartition) = RouterComm.NullMsg then
                OutBox(RouterComm.AuditPartition) := RouterComm.EmptyAuditMsg(Partition); 
            end if;

            -- Loop through all the outbox cells and apply guarding policies on all the non-null messages
            for I in RouterComm.PartitionID range 1..4 loop
                if (OutBox(I) /= RouterComm.NullMsg ) then
                    If (CommPolicy(I)) then
                   
                        ApplyGuardPolicy(GuardPolicy(I), Partition, OutBox(I),Result);
                     	if Result /= 0 then
                     		 Audit(Result, Partition, I, OutBox(RouterComm.AuditPartition));
                     	end if;
                    else
                        Audit(NotAllowedToCommunicate, Partition, I, OutBox(RouterComm.AuditPartition));
                        OutBox(I) :=  RouterComm.NullMsg;
                    end if;
                end if;
            end loop;
            
            -- Send messages to destination
            for I in RouterComm.PartitionID range 1..4 loop
                -- if the message is not null, it means the message has passed all the policies
                if (OutBox(I) /= RouterComm.NullMsg) then
                    case I is
                        -- Note that we make the Audit partition symmetric to the other partitions. When I=0, we are sending to the Audit partition.
                        when 0 => Interface0.InBox(Partition) := OutBox(I);
                        when 1 => Interface1.InBox(Partition) := OutBox(I);
                        when 2 => Interface2.InBox(Partition) := OutBox(I);
                        when 3 => Interface3.InBox(Partition) := OutBox(I);
			when 4 => Interface4.InBox(Partition) := OutBox(I);
                    end case;
                end if;
            end loop;

        end ProcessOnePartition;
    
        procedure RouteMessage
           is
        begin

            ProcessOnePartition(0, Interface0.OutBox, Interface0.CommPolicy, Interface0.GuardPolicy);
            ProcessOnePartition(1, Interface1.OutBox, Interface1.CommPolicy, Interface1.GuardPolicy);
            ProcessOnePartition(2, Interface2.OutBox, Interface2.CommPolicy, Interface2.GuardPolicy);
            ProcessOnePartition(3, Interface3.OutBox, Interface3.CommPolicy, Interface3.GuardPolicy);
            ProcessOnePartition(4, Interface4.OutBox, Interface4.CommPolicy, Interface4.GuardPolicy);

        end RouteMessage;
end router;      
