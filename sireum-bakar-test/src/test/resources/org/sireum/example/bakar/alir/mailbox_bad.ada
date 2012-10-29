-- This is a SPARK-Ada version of the simple C mailbox example
-- provided by Rockwell Collins. The example is enriched with
-- SPARK annotations. The purpose of this simple program is to
-- transmit data from one entity to another through a mediator
-- (the mailbox).
--
-- @author Edwin Rodr�guez


-- TODO: Refactor the body of the loop of the Main procedure into a
--       separate procedure.
-- TODO: Add the new annotations reflecting conditional information
--       flow (derives ... from ... when ...).


package Mailbox
--# own INTEGER_INPUT_0_READY, INTEGER_INPUT_0_DATA,
--#     INTEGER_OUTPUT_0_READY, INTEGER_OUTPUT_0_DATA,
--#     INTEGER_INPUT_1_READY, INTEGER_INPUT_1_DATA,
--#     INTEGER_OUTPUT_1_READY, INTEGER_OUTPUT_1_DATA;
--# initializes INTEGER_INPUT_0_READY, INTEGER_INPUT_0_DATA,
--#     INTEGER_OUTPUT_0_READY, INTEGER_OUTPUT_0_DATA,
--#     INTEGER_INPUT_1_READY, INTEGER_INPUT_1_DATA,
--#     INTEGER_OUTPUT_1_READY, INTEGER_OUTPUT_1_DATA;

is

  INTEGER_INPUT_0_READY : BOOLEAN := TRUE;
  INTEGER_INPUT_0_DATA : INTEGER := -1;
  INTEGER_OUTPUT_0_READY : BOOLEAN := TRUE;
  INTEGER_OUTPUT_0_DATA : INTEGER := -1;

  INTEGER_INPUT_1_READY : BOOLEAN := TRUE;
  INTEGER_INPUT_1_DATA : INTEGER := -1;
  INTEGER_OUTPUT_1_READY : BOOLEAN := TRUE;
  INTEGER_OUTPUT_1_DATA : INTEGER := -1;

  function INPUT_0_CONSUMED return BOOLEAN;
  --# global in INTEGER_INPUT_0_READY;

  function INPUT_0_READY return BOOLEAN;
  --# global in INTEGER_INPUT_0_READY;

  function OUTPUT_0_CONSUMED return BOOLEAN;
  --# global in INTEGER_OUTPUT_0_READY;

  function OUTPUT_0_READY return BOOLEAN;
  --# global in INTEGER_OUTPUT_0_READY;

  function INPUT_1_CONSUMED return BOOLEAN;
  --# global in INTEGER_INPUT_1_READY;

  function INPUT_1_READY return BOOLEAN;
  --# global in INTEGER_INPUT_1_READY;

  function OUTPUT_1_CONSUMED return BOOLEAN;
  --# global in INTEGER_OUTPUT_1_READY;

  function OUTPUT_1_READY return BOOLEAN;
  --# global in INTEGER_OUTPUT_1_READY;

  procedure NOTIFY_INPUT_0_CONSUMED;
  --# global out INTEGER_INPUT_0_READY;
  --# derives INTEGER_INPUT_0_READY from ;

  procedure NOTIFY_INPUT_0_READY;
  --# global out INTEGER_INPUT_0_READY;
  --# derives INTEGER_INPUT_0_READY from ;

  procedure NOTIFY_OUTPUT_0_CONSUMED;
  --# global out INTEGER_OUTPUT_0_READY;
  --# derives INTEGER_OUTPUT_0_READY from ;

  procedure NOTIFY_OUTPUT_0_READY;
  --# global out INTEGER_OUTPUT_0_READY;
  --# derives INTEGER_OUTPUT_0_READY from ;

  procedure NOTIFY_INPUT_1_CONSUMED;
  --# global out INTEGER_INPUT_1_READY;
  --# derives INTEGER_INPUT_1_READY from ;

  procedure NOTIFY_INPUT_1_READY;
  --# global out INTEGER_INPUT_1_READY;
  --# derives INTEGER_INPUT_1_READY from ;

  procedure NOTIFY_OUTPUT_1_CONSUMED;
  --# global out INTEGER_OUTPUT_1_READY;
  --# derives INTEGER_OUTPUT_1_READY from ;

  procedure NOTIFY_OUTPUT_1_READY;
  --# global out INTEGER_OUTPUT_1_READY;
  --# derives INTEGER_OUTPUT_1_READY from ;

  function READ_INPUT_0 return INTEGER;
  --# global in INTEGER_INPUT_0_DATA;

  function READ_OUTPUT_0 return INTEGER;
  --# global in INTEGER_OUTPUT_0_DATA;

  function READ_INPUT_1 return INTEGER;
  --# global in INTEGER_INPUT_1_DATA;

  function READ_OUTPUT_1 return INTEGER;
  --# global in INTEGER_OUTPUT_1_DATA;

  procedure WRITE_INPUT_0(Data : in INTEGER);
  --# global out INTEGER_INPUT_0_DATA;
  --# derives INTEGER_INPUT_0_DATA from Data;

  procedure WRITE_OUTPUT_0(Data : in INTEGER);
  --# global out INTEGER_OUTPUT_0_DATA;
  --# derives INTEGER_OUTPUT_0_DATA from Data;

  procedure WRITE_INPUT_1(Data : in INTEGER);
  --# global out INTEGER_INPUT_1_DATA;
  --# derives INTEGER_INPUT_1_DATA from Data;

  procedure WRITE_OUTPUT_1(Data : in INTEGER);
  --# global out INTEGER_OUTPUT_1_DATA;
  --# derives INTEGER_OUTPUT_1_DATA from Data;

  procedure MACHINE_STEP ;
    --# global in out INTEGER_INPUT_0_READY,
    --#               INTEGER_INPUT_1_READY,
    --#               INTEGER_OUTPUT_0_READY,
    --#               INTEGER_OUTPUT_1_READY,
    --#               INTEGER_OUTPUT_0_DATA,
    --#               INTEGER_OUTPUT_1_DATA;
    --#        in     INTEGER_INPUT_0_DATA,
    --#               INTEGER_INPUT_1_DATA;
    --# derives INTEGER_OUTPUT_0_DATA from INTEGER_INPUT_1_DATA,
    --#                                              INTEGER_OUTPUT_0_READY,
    --#                                              INTEGER_OUTPUT_0_DATA,
    --#                                              INTEGER_INPUT_1_READY &
    --#         INTEGER_OUTPUT_1_DATA from INTEGER_INPUT_0_DATA,
    --#                                              INTEGER_INPUT_0_READY,
    --#                                              INTEGER_OUTPUT_1_DATA,
    --#                                              INTEGER_OUTPUT_1_READY &
    --#         INTEGER_INPUT_0_READY from INTEGER_INPUT_0_READY,
    --#                                              INTEGER_OUTPUT_1_READY &
    --#         INTEGER_INPUT_1_READY from INTEGER_INPUT_1_READY,
    --#                                              INTEGER_OUTPUT_0_READY &
    --#         INTEGER_OUTPUT_0_READY from INTEGER_OUTPUT_0_READY,
    --#                                               INTEGER_INPUT_1_READY &
    --#         INTEGER_OUTPUT_1_READY from INTEGER_OUTPUT_1_READY,
    --#                                               INTEGER_INPUT_0_READY;

  procedure Main;
  
  --# global in out INTEGER_INPUT_0_READY,
  --#               INTEGER_INPUT_1_READY,
  --#               INTEGER_OUTPUT_0_READY,
  --#               INTEGER_OUTPUT_1_READY,
  --#               INTEGER_OUTPUT_0_DATA,
  --#               INTEGER_OUTPUT_1_DATA;
  --#        in     INTEGER_INPUT_0_DATA,
  --#               INTEGER_INPUT_1_DATA;
  --# derives INTEGER_OUTPUT_0_DATA from INTEGER_INPUT_1_DATA,
  --#                                              INTEGER_OUTPUT_0_READY,
  --#                                              INTEGER_OUTPUT_0_DATA,
  --#                                              INTEGER_INPUT_1_READY &
  --#         INTEGER_OUTPUT_1_DATA from INTEGER_INPUT_0_DATA,
  --#                                              INTEGER_INPUT_0_READY,
  --#                                              INTEGER_OUTPUT_1_DATA,
  --#                                              INTEGER_OUTPUT_1_READY &
  --#         INTEGER_INPUT_0_READY from INTEGER_INPUT_0_READY,
  --#                                              INTEGER_OUTPUT_1_READY &
  --#         INTEGER_INPUT_1_READY from INTEGER_INPUT_1_READY,
  --#                                              INTEGER_OUTPUT_0_READY &
  --#         INTEGER_OUTPUT_0_READY from INTEGER_OUTPUT_0_READY,
  --#                                               INTEGER_INPUT_1_READY &
  --#         INTEGER_OUTPUT_1_READY from INTEGER_OUTPUT_1_READY,
  --#                                               INTEGER_INPUT_0_READY;

end Mailbox;

package body Mailbox
is

  function INPUT_0_CONSUMED return BOOLEAN is
  begin
    return not INTEGER_INPUT_0_READY;
  end INPUT_0_CONSUMED;

  function INPUT_0_READY return BOOLEAN is
  begin
    return INTEGER_INPUT_0_READY;
  end INPUT_0_READY;

  function OUTPUT_0_CONSUMED return BOOLEAN is
  begin
    return not INTEGER_OUTPUT_0_READY;
  end OUTPUT_0_CONSUMED;

  function OUTPUT_0_READY return BOOLEAN is
  begin
    return INTEGER_OUTPUT_0_READY;
  end OUTPUT_0_READY;

  function INPUT_1_CONSUMED return BOOLEAN is
  begin
    return not INTEGER_INPUT_1_READY;
  end INPUT_1_CONSUMED;

  function INPUT_1_READY return BOOLEAN is
  begin
    return INTEGER_INPUT_1_READY;
  end INPUT_1_READY;

  function OUTPUT_1_CONSUMED return BOOLEAN is
  begin
    return not INTEGER_OUTPUT_1_READY;
  end OUTPUT_1_CONSUMED;

  function OUTPUT_1_READY return BOOLEAN is
  begin
    return INTEGER_OUTPUT_1_READY;
  end OUTPUT_1_READY;

  procedure NOTIFY_INPUT_0_CONSUMED is
  begin
    INTEGER_INPUT_0_READY := FALSE;
  end NOTIFY_INPUT_0_CONSUMED;

  procedure NOTIFY_INPUT_0_READY is
  begin
    INTEGER_INPUT_0_READY := TRUE;
  end NOTIFY_INPUT_0_READY;

  procedure NOTIFY_OUTPUT_0_CONSUMED is
  begin
    INTEGER_OUTPUT_0_READY := FALSE;
  end NOTIFY_OUTPUT_0_CONSUMED;

  procedure NOTIFY_OUTPUT_0_READY is
  begin
    INTEGER_OUTPUT_0_READY := TRUE;
  end NOTIFY_OUTPUT_0_READY;

  procedure NOTIFY_INPUT_1_CONSUMED is
  begin
    INTEGER_INPUT_1_READY := FALSE;
  end NOTIFY_INPUT_1_CONSUMED;

  procedure NOTIFY_INPUT_1_READY is
  begin
    INTEGER_INPUT_1_READY := TRUE;
  end NOTIFY_INPUT_1_READY;

  procedure NOTIFY_OUTPUT_1_CONSUMED is
  begin
    INTEGER_OUTPUT_1_READY := FALSE;
  end NOTIFY_OUTPUT_1_CONSUMED;

  procedure NOTIFY_OUTPUT_1_READY is
  begin
    INTEGER_OUTPUT_1_READY := TRUE;
  end NOTIFY_OUTPUT_1_READY;

  function READ_INPUT_0 return INTEGER is
  begin
  return INTEGER_INPUT_0_DATA;
  end READ_INPUT_0;

  function READ_OUTPUT_0 return INTEGER is
  begin
    return INTEGER_OUTPUT_0_DATA;
  end READ_OUTPUT_0;

  function READ_INPUT_1 return INTEGER is
  begin
    return INTEGER_INPUT_1_DATA;
  end READ_INPUT_1;

  function READ_OUTPUT_1 return INTEGER is
  begin
    <<tag>>return INTEGER_OUTPUT_1_DATA;
  end READ_OUTPUT_1;

  procedure WRITE_INPUT_0(Data : in INTEGER) is
  begin
    INTEGER_INPUT_0_DATA := Data;
  end WRITE_INPUT_0;

  procedure WRITE_OUTPUT_0(Data : in INTEGER) is
  begin
    <<test>>INTEGER_OUTPUT_0_DATA := Data;
  end WRITE_OUTPUT_0;

  procedure WRITE_INPUT_1(Data : in INTEGER) is
  begin
    INTEGER_INPUT_1_DATA := Data;
  end WRITE_INPUT_1;

  procedure WRITE_OUTPUT_1(Data : in INTEGER) is
  begin
    INTEGER_OUTPUT_1_DATA := Data;
  end WRITE_OUTPUT_1;

  procedure MACHINE_STEP
  is
      DATA : INTEGER;
    begin
      if INPUT_0_READY and OUTPUT_1_CONSUMED then
        DATA := READ_INPUT_0;
        NOTIFY_INPUT_0_CONSUMED;
        WRITE_OUTPUT_1(DATA);
        NOTIFY_OUTPUT_1_READY;
      end if;
      if INPUT_1_READY and OUTPUT_0_CONSUMED then
        DATA := READ_INPUT_1;
        NOTIFY_INPUT_1_CONSUMED;
        WRITE_OUTPUT_0(DATA);
        NOTIFY_OUTPUT_0_READY;
      end if;
  end MACHINE_STEP;

  procedure Main
  is
  begin
   for J in Integer range 1..10 loop
     MACHINE_STEP;
   end loop ; 
  end Main;
end Mailbox;
