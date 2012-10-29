package case_statement
is
  type Week is (Mon, Tue, Wed, Thur, Fri, FriPart2, Sat, Sun);
  subtype WeekDay is Week range Week'First .. Fri; -- Week'Pred(Week'Pred(Week'Pred(Week'Last)));
  subtype HumpDay is WeekDay range Wed .. Wed;
  subtype WeekEnd is Week range Sat .. Week'Last;
  
  type Activity is (Work, Sleep, Party, Drink);
  
  function isWorkDay (d : Week) return Activity;

end case_statement;

package body case_statement
is

  function isWorkDay (d : Week) return Activity
  is
    ret : Activity;
  begin

    case d is
      when WeekDay range Mon .. Tue =>
        ret := Work;
      when HumpDay =>
        ret := Sleep;
      when Thur =>
        ret := Sleep;
      when Fri | FriPart2 =>
        ret := Party;
      when WeekEnd => 
        ret := Drink; 
    end case;

    return ret;  
  end isWorkDay;
  
end case_statement;

