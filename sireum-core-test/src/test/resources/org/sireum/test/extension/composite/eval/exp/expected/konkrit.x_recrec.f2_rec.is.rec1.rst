Exp: @@x_recrec.f2_rec

State: Before

* Global Variables

  * @@x_recrec = CRecord#2

    * f1_rec = CRecord#0

      * f1 = 11

      * f2 = 12

    * f2_rec = CRecord#1

      * f1 = 21

      * f2 = 22

----

State: After

* Global Variables

  * @@x_recrec = CRecord#2

    * f1_rec = CRecord#0

      * f1 = 11

      * f2 = 12

    * f2_rec = CRecord#1

      * f1 = 21

      * f2 = 22

* Result: CRecord#1
