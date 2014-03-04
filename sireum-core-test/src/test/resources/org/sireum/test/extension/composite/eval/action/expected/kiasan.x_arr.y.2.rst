Action: @@x_arr[@@y] := 2;

State: Before



----

+---------------------------------+---------------------------------+
|                                 |                                 |
| State: After                    | State: After (Semi-Concrete)    |
|                                 |                                 |
| * Global Variables              | * Global Variables              |
|                                 |                                 |
|   * @@x_arr = KArray#1          |   * @@x_arr = KArray#1          |
|                                 |                                 |
|     * min=i1; length=i2; max=i3 |     * min=0; length=1; max=0    |
|                                 |                                 |
|     * [i4] = 2                  |     * [i4] = 2                  |
|                                 |                                 |
|   * @@y = i4                    |   * @@y = i4                    |
|                                 |                                 |
| * Path Conditions               | * Path Conditions               |
|                                 |                                 |
|   * i1 <= i3                    |   * 0 <= 0                      |
|                                 |                                 |
|   * 0 <= i2                     |   * 0 <= 1                      |
|                                 |                                 |
|   * i2 == i3 - i1 + 1           |   * 1 == 0 - 0 + 1              |
|                                 |                                 |
|   * 1 <= i2                     |   * 1 <= 1                      |
|                                 |                                 |
+---------------------------------+---------------------------------+
