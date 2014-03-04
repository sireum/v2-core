Exp: @@x_recarr.f1_arr[2]

State: Before

* Global Variables

  * @@x_recarr = CRecord#2

    * f1_arr = CArray#0

      * min=1; length=2; max=2

      * [1] = 11

      * [2] = 12

    * f2_arr = CArray#1

      * min=1; length=2; max=2

      * [1] = 22

      * [2] = 23

----

State: After

* Global Variables

  * @@x_recarr = CRecord#2

    * f1_arr = CArray#0

      * min=1; length=2; max=2

      * [1] = 11

      * [2] = 12

    * f2_arr = CArray#1

      * min=1; length=2; max=2

      * [1] = 22

      * [2] = 23

* Result: 12
