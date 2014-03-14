Action: @@x_recarr.f1_rec := @@y_arr;

State: Before



----

+-----------------------------------+-----------------------------------+
|                                   |                                   |
| State: After                      | State: After (Semi-Concrete)      |
|                                   |                                   |
| * Global Variables                | * Global Variables                |
|                                   |                                   |
|   * @@x_recarr = KRecord#3        |   * @@x_recarr = KRecord#3        |
|                                   |                                   |
|     * f1_rec = KArray#1           |     * f1_rec = KArray#1           |
|                                   |                                   |
|       * min=i0; length=i1; max=i2 |       * min=0; length=1; max=0    |
|                                   |                                   |
|   * @@y_arr = KArray#1            |   * @@y_arr = KArray#1            |
|                                   |                                   |
|     * min=i0; length=i1; max=i2   |     * min=0; length=1; max=0      |
|                                   |                                   |
| * Path Conditions                 | * Path Conditions                 |
|                                   |                                   |
|   * i0 <= i2                      |   * 0 <= 0                        |
|                                   |                                   |
|   * 0 <= i1                       |   * 0 <= 1                        |
|                                   |                                   |
|   * i1 == i2 - i0 + 1             |   * 1 == 0 - 0 + 1                |
|                                   |                                   |
+-----------------------------------+-----------------------------------+
