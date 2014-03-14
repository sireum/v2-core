Exp: @@x_arr[@@z]

State: Before

* Global Variables

  * @@x_arr = KArray#0

    * min=i0; length=i1; max=i2

    * [i3] = i4

  * @@y = i3

* Path Conditions

  * i0 <= i2

  * 0 <= i1

  * i1 == i2 - i0 + 1

  * 1 <= i1

----

+---------------------------------+---------------------------------+
|                                 |                                 |
| State: After                    | State: After (Semi-Concrete)    |
|                                 |                                 |
| * Global Variables              | * Global Variables              |
|                                 |                                 |
|   * @@x_arr = KArray#0          |   * @@x_arr = KArray#0          |
|                                 |                                 |
|     * min=i0; length=i1; max=i2 |     * min=-1; length=2; max=0   |
|                                 |                                 |
|     * [i3] = i4                 |     * [6] = i4                  |
|                                 |                                 |
|     * [i5] = i6                 |     * [5] = i6                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 6                     |
|                                 |                                 |
|   * @@z = i5                    |   * @@z = 5                     |
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
|   * i5 != i3                    |   * 5 != 6                      |
|                                 |                                 |
| * Result: i6                    | * Result: i6                    |
|                                 |                                 |
+---------------------------------+---------------------------------+

----

+---------------------------------+---------------------------------+
|                                 |                                 |
| State: After                    | State: After (Semi-Concrete)    |
|                                 |                                 |
| * Global Variables              | * Global Variables              |
|                                 |                                 |
|   * @@x_arr = KArray#0          |   * @@x_arr = KArray#0          |
|                                 |                                 |
|     * min=i0; length=i1; max=i2 |     * min=0; length=1; max=0    |
|                                 |                                 |
|     * [i3] = i4                 |     * [4] = i4                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 4                     |
|                                 |                                 |
|   * @@z = i5                    |   * @@z = 4                     |
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
|   * i5 == i3                    |   * 4 == 4                      |
|                                 |                                 |
| * Result: i4                    | * Result: i4                    |
|                                 |                                 |
+---------------------------------+---------------------------------+
