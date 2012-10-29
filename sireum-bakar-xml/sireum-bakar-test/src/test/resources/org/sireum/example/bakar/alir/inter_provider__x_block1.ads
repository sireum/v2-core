package inter_provider 
        --# own A,B,C;

        is
    A : Integer; 
    B : Integer;
    C : Integer;

        procedure bar;
        --# global in A, B; out C;
        --# derives C from A ,B;

    end inter_provider;