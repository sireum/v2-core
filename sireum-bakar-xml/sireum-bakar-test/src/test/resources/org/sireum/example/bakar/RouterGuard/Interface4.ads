with RouterComm;
--# inherit RouterComm;
package Interface4
        --# own OutBox;
        --#     InBox;
        --#     CommPolicy;
        --#     GuardPolicy;
        --# initializes OutBox, InBox, CommPolicy, GuardPolicy;
        is
    OutBox: RouterComm.BufferType := RouterComm.EmptyBuffer;
    InBox: RouterComm.BufferType := RouterComm.EmptyBuffer;
    CommPolicy: RouterComm.CommPolicyType := RouterComm.AllDenyPolicy;
    GuardPolicy: RouterComm.GuardPolicyType := RouterComm.DummyGuardPolicy;
end Interface4; 



