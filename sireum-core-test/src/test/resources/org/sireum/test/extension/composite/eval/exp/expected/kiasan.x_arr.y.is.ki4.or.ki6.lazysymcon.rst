Exp: @@x_arr[@@y]

State: Before

* Global Variables

  * @@x_arr = KArray#0

    * min=i1; length=i2; max=i3

    * [1] = i4

* Path Conditions

  * i1 <= i3

  * 0 <= i2

  * i2 == i3 - i1 + 1

  * 1 <= i2

----

+---------------------------------+---------------------------------+
|                                 |                                 |
| State: After                    | State: After (Semi-Concrete)    |
|                                 |                                 |
| * Global Variables              | * Global Variables              |
|                                 |                                 |
|   * @@x_arr = KArray#0          |   * @@x_arr = KArray#0          |
|                                 |                                 |
|     * min=i1; length=i2; max=i3 |     * min=0; length=2; max=1    |
|                                 |                                 |
|     * [1] = i4                  |     * [1] = i4                  |
|                                 |                                 |
|     * [i5] = i6                 |     * [4] = i6                  |
|                                 |                                 |
|   * @@y = i5                    |   * @@y = 4                     |
|                                 |                                 |
| * Path Conditions               | * Path Conditions               |
|                                 |                                 |
|   * i1 <= i3                    |   * 0 <= 1                      |
|                                 |                                 |
|   * 0 <= i2                     |   * 0 <= 2                      |
|                                 |                                 |
|   * i2 == i3 - i1 + 1           |   * 2 == 1 - 0 + 1              |
|                                 |                                 |
|   * 1 <= i2                     |   * 1 <= 2                      |
|                                 |                                 |
|   * 2 <= i2                     |   * 2 <= 2                      |
|                                 |                                 |
|   * i5 != 1                     |   * 4 != 1                      |
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
|     * min=i1; length=i2; max=i3 |     * min=0; length=1; max=0    |
|                                 |                                 |
|     * [1] = i4                  |     * [1] = i4                  |
|                                 |                                 |
|   * @@y = i5                    |   * @@y = 1                     |
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
|   * i5 == 1                     |   * 1 == 1                      |
|                                 |                                 |
| * Result: i4                    | * Result: i4                    |
|                                 |                                 |
+---------------------------------+---------------------------------+
