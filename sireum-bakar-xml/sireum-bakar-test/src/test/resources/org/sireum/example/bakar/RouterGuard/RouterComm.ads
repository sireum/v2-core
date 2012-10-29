
package RouterComm
        is
            -- Some useful constants
            subtype PartitionID is Integer range 0..4;
            type PayloadType is array (PartitionID) of Integer;   
            type MsgType is record
                Source : PartitionID;
                Destination : PartitionID;
                Payload : PayloadType;
            end record;
        
            type BufferType is array (PartitionID) of MsgType;
            type CommPolicyType is array (PartitionID) of Boolean;
            type PolicyType is array (PartitionID) of boolean;
            type GuardPolicyType is array (PartitionID) of PolicyType;
            -- PolicyType needs to be defined
        
            NullMsg: constant MsgType := MsgType'(0, 0, PayloadType'(0,0,0,0,0));
            EmptyBuffer: constant BufferType := BufferType'(others => NullMsg);
            AllDenyPolicy: constant CommPolicyType := CommPolicyType'(others => False);
            DummyGuardPolicy: constant GuardPolicyType := GuardPolicytype'(others => PolicyType'(others => False));
            AuditPartition: constant PartitionID := 0;
            function EmptyAuditMsg(Src: PartitionID) return MsgType;
            -- create an empty audit message from partition Src
end RouterComm;	
