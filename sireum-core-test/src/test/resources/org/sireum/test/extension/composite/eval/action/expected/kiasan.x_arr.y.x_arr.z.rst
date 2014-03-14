Action: @@x_arr[@@y] := @@x_arr[@@z];

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
|     * min=i0; length=i1; max=i2 |     * min=-1; length=2; max=0   |
|                                 |                                 |
|     * [i3] = i5                 |     * [5] = i5                  |
|                                 |                                 |
|     * [i4] = i5                 |     * [6] = i5                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 5                     |
|                                 |                                 |
|   * @@z = i4                    |   * @@z = 6                     |
|                                 |                                 |
| * Path Conditions               | * Path Conditions               |
|                                 |                                 |
|   * i0 <= i2                    |   * -1 <= 0                     |
|                                 |                                 |
|   * 0 <= i1                     |   * 0 <= 2                      |
|                                 |                                 |
|   * i1 == i2 - i0 + 1           |   * 2 == 0 - -1 + 1             |
|                                 |                                 |
|   * 1 <= i1                     |   * 1 <= 2                      |
|                                 |                                 |
|   * 2 <= i1                     |   * 2 <= 2                      |
|                                 |                                 |
|   * i3 != i4                    |   * 5 != 6                      |
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
|     * [i3] = i5                 |     * [4] = i5                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 4                     |
|                                 |                                 |
|   * @@z = i4                    |   * @@z = 4                     |
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
|   * i3 == i4                    |   * 4 == 4                      |
|                                 |                                 |
+---------------------------------+---------------------------------+
