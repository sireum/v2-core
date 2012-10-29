
package Router
  --#own OutBox0, InBox0, CommPolicy0, OutBox1, InBox1, CommPolicy1;
  --#initializes OutBox0, InBox0, CommPolicy0, OutBox1, InBox1, CommPolicy1;
is
   subtype PartitionID is Integer range 0..1;
   type PayloadType is array (PartitionID) of Integer;   
   type MsgType is record
      Source : PartitionID;
      Destination : PartitionID;
      Payload : PayloadType;
   end record;
   
   type BufferType is array (PartitionID) of MsgType;
   type CommPolicyType is array (PartitionID) of Boolean;
   
   -- PolicyType needs to be defined
   NullMsg: constant MsgType := MsgType'(0, 0, PayloadType'(0,0));
   EmptyBuffer: constant BufferType := BufferType'(others => NullMsg);
   AllDenyPolicy: constant CommPolicyType := CommPolicyType'(others => False);
   AuditPartition: constant PartitionID := 0;
   
   OutBox0: BufferType := EmptyBuffer;
   InBox0: BufferType := EmptyBuffer;
   CommPolicy0: CommPolicyType := AllDenyPolicy;
   
   OutBox1: BufferType := EmptyBuffer;
   InBox1: BufferType := EmptyBuffer;
   CommPolicy1: CommPolicyType := AllDenyPolicy;
   
   procedure ProcessOnePartition(Partition: in PartitionID; 
				 OutBox: in out BufferType; 
				 CommPolicy: in CommPolicyType;
				 K: in PartitionID; X: out MsgType);
   
   --# global in out InBox0, InBox1;
   --# derives OutBox from OutBox, CommPolicy &
   --#         InBox0 from InBox0, Partition, OutBox, CommPolicy &
   --#         InBox1 from InBox1, Partition, OutBox, CommPolicy &
   --#         X      from InBox1, Partition, OutBox, CommPolicy, K;
                                                                  
end Router;

package body Router
        is

   procedure ProcessOnePartition(Partition: in PartitionID; 
				 OutBox: in out BufferType; 
				 CommPolicy: in CommPolicyType;
				 K: in PartitionID; X: out MsgType)
     -- Process one partition
   is
            begin
        
            -- If the partition does not have a message to be sent to audit, create an empty audit message and put it in the audit outbox cell
            -- Alternatively, we could make the Audit() procedure to check whether the AuditMsg is null.
	  
            --If OutBox(AuditPartition) = NullMsg then
            --    OutBox(AuditPartition) := RouterComm.EmptyAuditMsg(Partition); 
            --end if;

            -- Loop through all the outbox cells and apply guarding policies on all the non-null messages
            for I in PartitionID range 0..1 loop
                if (OutBox(I) /= NullMsg ) then
		   if CommPolicy(I) /= true then         
                        OutBox(I) :=  NullMsg;
                   end if;
                end if;
            end loop;
            
            -- Send messages to destination
            for I in PartitionID range 0..1 loop
                -- if the message is not null, it means the message has passed all the policies
                if (OutBox(I) /= NullMsg) then
                    if I = 0 then
                        -- Note that we make the Audit partition symmetric to the other partitions. When I=0, we are sending to the Audit partition.
		       InBox0(Partition) := OutBox(I);
		    else
                       InBox1(Partition) := OutBox(I);
                    end if;
                end if;
		X := InBox1(K);
            end loop;

        end ProcessOnePartition;
    
      
end Router;      
