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
|     * min=i1; length=i2; max=i3   |     * min=0; length=1; max=0      |
|                                   |                                   |
|     * [1] = KArray#1              |     * [1] = KArray#1              |
|                                   |                                   |
|       * min=i4; length=i5; max=i6 |       * min=0; length=1; max=0    |
|                                   |                                   |
|   * @@y_arr = KArray#1            |   * @@y_arr = KArray#1            |
|                                   |                                   |
|     * min=i4; length=i5; max=i6   |     * min=0; length=1; max=0      |
|                                   |                                   |
| * Path Conditions                 | * Path Conditions                 |
|                                   |                                   |
|   * i1 <= i3                      |   * 0 <= 0                        |
|                                   |                                   |
|   * 0 <= i2                       |   * 0 <= 1                        |
|                                   |                                   |
|   * i2 == i3 - i1 + 1             |   * 1 == 0 - 0 + 1                |
|                                   |                                   |
|   * i4 <= i6                      |   * 0 <= 0                        |
|                                   |                                   |
|   * 0 <= i5                       |   * 0 <= 1                        |
|                                   |                                   |
|   * i5 == i6 - i4 + 1             |   * 1 == 0 - 0 + 1                |
|                                   |                                   |
|   * i7 <= i9                      |   * 0 <= 0                        |
|                                   |                                   |
|   * 0 <= i8                       |   * 0 <= 1                        |
|                                   |                                   |
|   * i8 == i9 - i7 + 1             |   * 1 == 0 - 0 + 1                |
|                                   |                                   |
|   * 1 <= i2                       |   * 1 <= 1                        |
|                                   |                                   |
+-----------------------------------+-----------------------------------+
