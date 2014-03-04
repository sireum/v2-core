Exp: @@x_arr[2]

State: Before

* Global Variables

  * @@x_arr = KArray#0

    * min=i1; length=i2; max=i3

    * [1] = i6

    * [i4] = i5

  * @@y = i4

* Path Conditions

  * i1 <= i3

  * 0 <= i2

  * i2 == i3 - i1 + 1

  * 1 <= i2

  * 2 <= i2

  * 1 != i4

----

+---------------------------------+---------------------------------+
|                                 |                                 |
| State: After                    | State: After (Semi-Concrete)    |
|                                 |                                 |
| * Global Variables              | * Global Variables              |
|                                 |                                 |
|   * @@x_arr = KArray#0          |   * @@x_arr = KArray#0          |
|                                 |                                 |
|     * min=i1; length=i2; max=i3 |     * min=-2; length=3; max=0   |
|                                 |                                 |
|     * [1] = i6                  |     * [1] = i6                  |
|                                 |                                 |
|     * [2] = i7                  |     * [2] = i7                  |
|                                 |                                 |
|     * [i4] = i5                 |     * [6] = i5                  |
|                                 |                                 |
|   * @@y = i4                    |   * @@y = 6                     |
|                                 |                                 |
| * Path Conditions               | * Path Conditions               |
|                                 |                                 |
|   * i1 <= i3                    |   * -2 <= 0                     |
|                                 |                                 |
|   * 0 <= i2                     |   * 0 <= 3                      |
|                                 |                                 |
|   * i2 == i3 - i1 + 1           |   * 3 == 0 - -2 + 1             |
|                                 |                                 |
|   * 1 <= i2                     |   * 1 <= 3                      |
|                                 |                                 |
|   * 2 <= i2                     |   * 2 <= 3                      |
|                                 |                                 |
|   * 1 != i4                     |   * 1 != 6                      |
|                                 |                                 |
|   * 3 <= i2                     |   * 3 <= 3                      |
|                                 |                                 |
|   * 2 != i4                     |   * 2 != 6                      |
|                                 |                                 |
| * Result: i7                    | * Result: i7                    |
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
|     * min=i1; length=i2; max=i3 |     * min=-1; length=2; max=0   |
|                                 |                                 |
|     * [2] = i5                  |     * [2] = i5                  |
|                                 |                                 |
|     * [1] = i6                  |     * [1] = i6                  |
|                                 |                                 |
|   * @@y = i4                    |   * @@y = 2                     |
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
|   * 1 != i4                     |   * 1 != 2                      |
|                                 |                                 |
|   * 2 == i4                     |   * 2 == 2                      |
|                                 |                                 |
| * Result: i5                    | * Result: i5                    |
|                                 |                                 |
+---------------------------------+---------------------------------+
