package Mailbox

--# own INTEGER_INPUT_0_READY;
--# initializes INTEGER_INPUT_0_READY;

is

  INTEGER_INPUT_0_READY : BOOLEAN := TRUE;

  function INPUT_0_READY return BOOLEAN;
  --# global in INTEGER_INPUT_0_READY;

  procedure NOTIFY_INPUT_0_READY(A: in Integer; C: out Integer);
  --# global out INTEGER_INPUT_0_READY;
  --# derives C from A 
  --#	    & INTEGER_INPUT_0_READY from ;

 procedure MACHINE_STEP(A: in Integer; C: out Integer) ;
    --# global out INTEGER_INPUT_0_READY ;
    --# derives C from A 
    --#	      & INTEGER_INPUT_0_READY from ;

end Mailbox;

package body Mailbox
is

function INPUT_0_READY return BOOLEAN is
  begin
    return INTEGER_INPUT_0_READY;
  end INPUT_0_READY;

  procedure NOTIFY_INPUT_0_READY(A: in Integer; C: out Integer) is
  begin
    C := A;
    INTEGER_INPUT_0_READY := TRUE;
  end NOTIFY_INPUT_0_READY;

procedure MACHINE_STEP(A: in Integer; C: out Integer)
  is
    begin
         NOTIFY_INPUT_0_READY(A,C);
  end MACHINE_STEP;

end Mailbox;
