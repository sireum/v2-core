
with RouterComm,Interface0,Interface1,Interface2,Interface3,Interface4; use type routerComm.MsgType;
--# inherit RouterComm, Interface0, Interface1, Interface2, Interface3, Interface4;


package Router
        is


    --- Some audit codes
    NotAllowedToCommunicate: constant Integer := 0;
    
    procedure Audit(AuditCode: in Integer; Src: in RouterComm.PartitionID; Dst: in RouterComm.PartitionID; AuditMsg: in out RouterComm.MsgType);
    -- append an audit code to an audit message for communication between Src and Dst. Unimplemented at the moment.
	--# derives AuditMsg from AuditCode, Src, Dst, AuditMsg;


   -- procedure AllowedToCommunicate(CommPolicy: in RouterComm.CommPolicyType; Src: RouterComm.PartitionID; Dst: RouterComm.PartitionID; AuditMsg: out RouterComm.MsgType; Result: out Boolean);
    -- check whether the interface is allowed to communicate to a partition ;

    procedure ApplyGuardPolicy(Policy: in RouterComm.PolicyType; Src: in RouterComm.PartitionID; OutMsg: in out RouterComm.MsgType; Result : out Integer);
    -- apply the guardng policy to the message in place. If fails, zero-out the OutMsg. Unimplemented at the moment.
    --# derives OutMsg from Policy,src, OutMsg &
    --#              Result from Policy,Src;
    

    procedure ProcessOnePartition(Partition: in RouterComm.PartitionID; OutBox: in out RouterComm.BufferType; CommPolicy: in RouterComm.CommPolicyType; GuardPolicy: in RouterComm.GuardPolicyType);
        --# global in out Interface1.Inbox;
        --#        in out Interface2.Inbox;
        --#        in out Interface3.Inbox;
        --#        in out Interface4.Inbox;
	--#        in out Interface0.Inbox;
	--# derives OutBox from Partition, OutBox, CommPolicy, GuardPolicy &
	--#         Interface0.Inbox from Interface0.Inbox, Partition, OutBox, CommPolicy, GuardPolicy &
	--#         Interface1.Inbox from Interface1.Inbox, Partition, OutBox, CommPolicy, GuardPolicy &
	--#         Interface2.Inbox from Interface2.Inbox, Partition, OutBox, CommPolicy, GuardPolicy &
	--#         Interface3.Inbox from Interface3.Inbox, Partition, OutBox, CommPolicy, GuardPolicy &
	--#         Interface4.Inbox from Interface4.Inbox, Partition, OutBox, CommPolicy, GuardPolicy ;
    
    procedure RouteMessage;
        --# global in out Interface1.OutBox;
        --#        in out Interface1.InBox;
        --#        in Interface1.CommPolicy;
        --#        in Interface1.GuardPolicy;
        --#        in out Interface2.OutBox;
        --#        in out Interface2.InBox;
        --#        in Interface2.CommPolicy;
        --#        in Interface2.GuardPolicy;
        --#        in out Interface3.OutBox;
        --#        in out Interface3.InBox;
        --#        in Interface3.CommPolicy;
        --#        in Interface3.GuardPolicy;
        --#        in out Interface4.OutBox;
        --#        in out Interface4.InBox;
        --#        in Interface4.CommPolicy;
        --#        in Interface4.GuardPolicy;
        --#        in out Interface0.OutBox;
        --#        in out Interface0.InBox;
        --#        in Interface0.CommPolicy;
        --#        in Interface0.GuardPolicy;
	--# derives Interface1.OutBox from Interface1.OutBox,  Interface1.CommPolicy, Interface1.GuardPolicy &
	--#	    Interface1.InBox from Interface1.InBox,
	--#				  Interface1.OutBox, Interface2.OutBox, Interface3.OutBox, Interface4.OutBox,Interface0.OutBox,
	--#				  Interface1.CommPolicy, Interface2.CommPolicy, Interface3.CommPolicy, Interface4.CommPolicy, Interface0.CommPolicy, 
	--#				  Interface1.GuardPolicy, Interface2.GuardPolicy, Interface3.GuardPolicy, Interface4.GuardPolicy, Interface0.GuardPolicy &
        --#	    Interface2.OutBox from Interface2.OutBox, Interface2.CommPolicy, Interface2.GuardPolicy &
	--#	    Interface2.InBox from Interface2.InBox,Interface1.OutBox, Interface2.OutBox, Interface3.OutBox, Interface4.OutBox,Interface0.OutBox,
	--#				  Interface1.CommPolicy, Interface2.CommPolicy, Interface3.CommPolicy, Interface4.CommPolicy, Interface0.CommPolicy, 
	--#				  Interface1.GuardPolicy, Interface2.GuardPolicy, Interface3.GuardPolicy, Interface4.GuardPolicy, Interface0.GuardPolicy &
        --#	    Interface3.OutBox from Interface3.OutBox, Interface3.CommPolicy, Interface3.GuardPolicy &
	--#	    Interface3.InBox from Interface3.InBox, Interface1.OutBox, Interface2.OutBox, Interface3.OutBox, Interface4.OutBox,Interface0.OutBox,
	--#				  Interface1.CommPolicy, Interface2.CommPolicy, Interface3.CommPolicy, Interface4.CommPolicy, Interface0.CommPolicy ,
	--#				  Interface1.GuardPolicy, Interface2.GuardPolicy, Interface3.GuardPolicy, Interface4.GuardPolicy, Interface0.GuardPolicy &  
        --#	    Interface4.OutBox from Interface4.OutBox, Interface4.CommPolicy, Interface4.GuardPolicy &
	--#	    Interface4.InBox from Interface4.InBox, Interface1.OutBox, Interface2.OutBox, Interface3.OutBox, Interface4.OutBox,Interface0.OutBox,
	--#				  Interface1.CommPolicy, Interface2.CommPolicy, Interface3.CommPolicy, Interface4.CommPolicy, Interface0.CommPolicy, 
	--#				  Interface1.GuardPolicy, Interface2.GuardPolicy, Interface3.GuardPolicy, Interface4.GuardPolicy, Interface0.GuardPolicy &
        --#	    Interface0.OutBox from Interface0.OutBox, Interface0.CommPolicy, Interface0.GuardPolicy &
	--#	    Interface0.InBox from Interface0.InBox, Interface1.OutBox, Interface2.OutBox, Interface3.OutBox, Interface4.OutBox,Interface0.OutBox,
	--#				  Interface1.CommPolicy, Interface2.CommPolicy, Interface3.CommPolicy, Interface4.CommPolicy, Interface0.CommPolicy, 
	--#				  Interface1.GuardPolicy, Interface2.GuardPolicy, Interface3.GuardPolicy, Interface4.GuardPolicy, Interface0.GuardPolicy ;                                                                             
end router;

