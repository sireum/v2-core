package Mailbox3

--# own INTEGER_INPUT_0_READY,INTEGER_INPUT_0_DATA,INTEGER_OUTPUT_1_DATA;
--# initializes INTEGER_INPUT_0_READY,INTEGER_INPUT_0_DATA,INTEGER_OUTPUT_1_DATA;

is

  INTEGER_INPUT_0_READY : BOOLEAN := TRUE;
  INTEGER_INPUT_0_DATA : INTEGER := -1;
    INTEGER_OUTPUT_1_DATA : INTEGER := -1;

  function INPUT_0_READY return BOOLEAN;
  --# global in INTEGER_INPUT_0_READY;
    
  function READ_INPUT_0 return INTEGER;
  --# global in INTEGER_INPUT_0_DATA;
    
    procedure WRITE_OUTPUT_1(Data : in INTEGER);
    --# global out INTEGER_OUTPUT_1_DATA;
    --# derives INTEGER_OUTPUT_1_DATA from Data;
    
 procedure MACHINE_STEP ;
    --# global in out INTEGER_OUTPUT_1_DATA;
    --#        in     INTEGER_INPUT_0_DATA ,INTEGER_INPUT_0_READY;
    --# derives INTEGER_OUTPUT_1_DATA from INTEGER_INPUT_0_DATA,
    --#                                              INTEGER_INPUT_0_READY,
    --#                                              INTEGER_OUTPUT_1_DATA;


end Mailbox;

package body Mailbox3
is

function INPUT_0_READY return BOOLEAN is
  begin
    return INTEGER_INPUT_0_READY;
  end INPUT_0_READY;
    
function READ_INPUT_0 return INTEGER is
  begin
    return INTEGER_INPUT_0_DATA;
  end READ_INPUT_0;
    
    procedure WRITE_OUTPUT_1(Data : in INTEGER) is
    begin
        INTEGER_OUTPUT_1_DATA := Data;
    end WRITE_OUTPUT_1;

    procedure MACHINE_STEP
            is
                DATA_0 : INTEGER;
                TDATA  : INTEGER;
                begin
                if INPUT_0_READY then
                    DATA_0 := READ_INPUT_0;
                    TDATA  := DATA_0;
                    WRITE_OUTPUT_1(TDATA);
                end if;      
            end MACHINE_STEP;
end Mailbox;
