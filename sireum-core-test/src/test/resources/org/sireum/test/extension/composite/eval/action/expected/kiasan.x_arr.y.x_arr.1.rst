Action: @@x_arr[@@y] := @@x_arr[1];

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
|     * min=i0; length=i1; max=i2 |     * min=0; length=2; max=1    |
|                                 |                                 |
|     * [1] = i4                  |     * [4] = i4                  |
|                                 |                                 |
|     * [i3] = i4                 |     * [1] = i4                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 4                     |
|                                 |                                 |
| * Path Conditions               | * Path Conditions               |
|                                 |                                 |
|   * i0 <= i2                    |   * 0 <= 1                      |
|                                 |                                 |
|   * 0 <= i1                     |   * 0 <= 2                      |
|                                 |                                 |
|   * i1 == i2 - i0 + 1           |   * 2 == 1 - 0 + 1              |
|                                 |                                 |
|   * 1 <= i1                     |   * 1 <= 2                      |
|                                 |                                 |
|   * 2 <= i1                     |   * 2 <= 2                      |
|                                 |                                 |
|   * i3 != 1                     |   * 4 != 1                      |
|                                 |                                 |
+---------------------------------+---------------------------------+

----

+---------------------------------+---------------------------------+
|                                 |                                 |
| State: After                    | State: After (Semi-Concrete)    |
|                                 |                                 |
| * Global Variables              | * Global Variables              |
|                                 |                                 |
|   * @@x_arr = KArray#1          |   * @@x_arr = KArray#1          |
|                                 |                                 |
|     * min=i0; length=i1; max=i2 |     * min=0; length=1; max=0    |
|                                 |                                 |
|     * [1] = i4                  |     * [1] = i4                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 1                     |
|                                 |                                 |
| * Path Conditions               | * Path Conditions               |
|                                 |                                 |
|   * i0 <= i2                    |   * 0 <= 0                      |
|                                 |                                 |
|   * 0 <= i1                     |   * 0 <= 1                      |
|                                 |                                 |
|   * i1 == i2 - i0 + 1           |   * 1 == 0 - 0 + 1              |
|                                 |                                 |
|   * 1 <= i1                     |   * 1 <= 1                      |
|                                 |                                 |
|   * i3 == 1                     |   * 1 == 1                      |
|                                 |                                 |
+---------------------------------+---------------------------------+
