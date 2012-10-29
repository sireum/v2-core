package body RouterComm
        is
            -- Some useful constants
           
        
            function EmptyAuditMsg(Src: PartitionID) return MsgType
            -- create an empty audit message from partition Src
                is
            begin
                return MsgType'(Src, AuditPartition, PayloadType'(others=>0));
            end EmptyAuditMsg;
        
end RouterComm;
    
        
