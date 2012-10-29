package case_statement
is
  type Activity is (Work, Party, Drink);
  subtype IntType is Integer range 1 .. 3;
  subtype SixType is Integer range 6 .. 6;
 
  function isWorkDay (d : Integer) return Activity;

end case_statement;

package body case_statement
is

  function isWorkDay (d : Integer) return Activity
  is
    ret : Activity;
  begin

    case d is
      when IntType =>
        ret := Work;
      when Integer range 4 .. 5 | SixType | 7 =>
        ret := Party;
      when 8 .. 9 =>
        ret := Party;
      when 10 =>
        ret := Work;
      when others => 
        ret := Drink; 
    end case;

    return ret;  
  end isWorkDay;
  
end case_statement;

