5.
unwrap c # 1.
instantiate int_M__1 with 1.
done
6.
replace c # 1 : not for_some(_1, _2) by for_all(_1, not _2) using quant.
y
replace h # 11 : not (_1 and _2) by not _1 or not _2 using logical.
list
replace c # 1 : not (_1 and _2) by not _1 or not _2 using logical.
y
replace c # 1 : not _1 or _2 by _1 -> _2 using logical.
y
replace c # 1 : not _1 = _2 by _1 <> _2 using negation(1).
y
unwrap h # 1.
unwrap c # 1.
inst int_M__1 with int_m__1.
replace c # 1 : int_m__1 >= 1 by not 1 > int_m__1 using negation.
y
replace c # 1 : not _1 > _2 by _1 <= _2 using negation.
y
done
7.
delete [3, 4, 5, 7, 8].
unwrap c # 1.
instantiate int_M__1 with loop__1__i + 1.
done
exit
