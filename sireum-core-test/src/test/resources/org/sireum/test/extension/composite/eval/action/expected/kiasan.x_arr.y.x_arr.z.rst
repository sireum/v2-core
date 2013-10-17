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
|     * min=i1; length=i2; max=i3 |     * min=-1; length=2; max=0   |
|                                 |                                 |
|     * [i4] = i6                 |     * [5] = i6                  |
|                                 |                                 |
|     * [i5] = i6                 |     * [6] = i6                  |
|                                 |                                 |
|   * @@y = i4                    |   * @@y = 5                     |
|                                 |                                 |
|   * @@z = i5                    |   * @@z = 6                     |
|                                 |                                 |
| * Path Conditions               | * Path Conditions               |
|                                 |                                 |
|   * i1 <= i3                    |   * -1 <= 0                     |
|                                 |                                 |
|   * 0 <= i2                     |   * 0 <= 2                      |
|                                 |                                 |
|   * i2 == i3 - i1 + 1           |   * 2 == 0 - -1 + 1             |
|                                 |                                 |
|   * 1 <= i2                     |   * 1 <= 2                      |
|                                 |                                 |
|   * 2 <= i2                     |   * 2 <= 2                      |
|                                 |                                 |
|   * i4 != i5                    |   * 5 != 6                      |
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
|     * min=i1; length=i2; max=i3 |     * min=0; length=1; max=0    |
|                                 |                                 |
|     * [i4] = i6                 |     * [4] = i6                  |
|                                 |                                 |
|   * @@y = i4                    |   * @@y = 4                     |
|                                 |                                 |
|   * @@z = i5                    |   * @@z = 4                     |
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
|   * i4 == i5                    |   * 4 == 4                      |
|                                 |                                 |
+---------------------------------+---------------------------------+
