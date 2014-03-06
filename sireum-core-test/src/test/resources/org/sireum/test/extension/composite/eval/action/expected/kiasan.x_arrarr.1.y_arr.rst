Action: @@x_arrarr[1] := @@y_arr;

State: Before



----

+-----------------------------------+-----------------------------------+
|                                   |                                   |
| State: After                      | State: After (Semi-Concrete)      |
|                                   |                                   |
| * Global Variables                | * Global Variables                |
|                                   |                                   |
|   * @@x_arrarr = KArray#3         |   * @@x_arrarr = KArray#3         |
|                                   |                                   |
|     * min=i0; length=i1; max=i2   |     * min=0; length=1; max=0      |
|                                   |                                   |
|     * [1] = KArray#1              |     * [1] = KArray#1              |
|                                   |                                   |
|       * min=i3; length=i4; max=i5 |       * min=0; length=1; max=0    |
|                                   |                                   |
|   * @@y_arr = KArray#1            |   * @@y_arr = KArray#1            |
|                                   |                                   |
|     * min=i3; length=i4; max=i5   |     * min=0; length=1; max=0      |
|                                   |                                   |
| * Path Conditions                 | * Path Conditions                 |
|                                   |                                   |
|   * i0 <= i2                      |   * 0 <= 0                        |
|                                   |                                   |
|   * 0 <= i1                       |   * 0 <= 1                        |
|                                   |                                   |
|   * i1 == i2 - i0 + 1             |   * 1 == 0 - 0 + 1                |
|                                   |                                   |
|   * i3 <= i5                      |   * 0 <= 0                        |
|                                   |                                   |
|   * 0 <= i4                       |   * 0 <= 1                        |
|                                   |                                   |
|   * i4 == i5 - i3 + 1             |   * 1 == 0 - 0 + 1                |
|                                   |                                   |
|   * i6 <= i8                      |   * 0 <= 0                        |
|                                   |                                   |
|   * 0 <= i7                       |   * 0 <= 1                        |
|                                   |                                   |
|   * i7 == i8 - i6 + 1             |   * 1 == 0 - 0 + 1                |
|                                   |                                   |
|   * 1 <= i1                       |   * 1 <= 1                        |
|                                   |                                   |
+-----------------------------------+-----------------------------------+
