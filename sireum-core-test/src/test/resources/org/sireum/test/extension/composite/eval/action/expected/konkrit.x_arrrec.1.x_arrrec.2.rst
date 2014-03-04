Action: @@x_arrrec[1] := @@x_arrrec[2];

State: Before

* Global Variables

  * @@x_arrrec = CArray#2

    * min=1; length=2; max=2

    * [1] = CRecord#0

      * f1 = 11

      * f2 = 12

    * [2] = CRecord#1

      * f1 = 21

      * f2 = 22

----

State: After

* Global Variables

  * @@x_arrrec = CArray#3

    * min=1; length=2; max=2

    * [1] = CRecord#1

      * f1 = 21

      * f2 = 22

    * [2] = CRecord#1

      * f1 = 21

      * f2 = 22
