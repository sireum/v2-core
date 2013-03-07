Action: @@x_recarr.f1_rec := @@y_arr;

State: Before



----

+-----------------------------------+-----------------------------------+
|                                   |                                   |
| State: After                      | State: After (Semi-Concrete)      |
|                                   |                                   |
| * Global Variables                | * Global Variables                |
|                                   |                                   |
|   * @@x_recarr = KRecord#2        |   * @@x_recarr = KRecord#2        |
|                                   |                                   |
|     * f1_rec = KArray#1           |     * f1_rec = KArray#1           |
|                                   |                                   |
|       * min=i1; length=i2; max=i3 |       * min=0; length=1; max=0    |
|                                   |                                   |
|   * @@y_arr = KArray#1            |   * @@y_arr = KArray#1            |
|                                   |                                   |
|     * min=i1; length=i2; max=i3   |     * min=0; length=1; max=0      |
|                                   |                                   |
| * Path Conditions                 | * Path Conditions                 |
|                                   |                                   |
|   * i1 <= i3                      |   * 0 <= 0                        |
|                                   |                                   |
|   * 0 <= i2                       |   * 0 <= 1                        |
|                                   |                                   |
|   * i2 == i3 - i1 + 1             |   * 1 == 0 - 0 + 1                |
|                                   |                                   |
+-----------------------------------+-----------------------------------+
