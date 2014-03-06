Exp: @@x_arr[2]

State: Before

* Global Variables

  * @@x_arr = KArray#0

    * min=i0; length=i1; max=i2

    * [1] = i5

    * [i3] = i4

  * @@y = i3

* Path Conditions

  * i0 <= i2

  * 0 <= i1

  * i1 == i2 - i0 + 1

  * 1 <= i1

  * 2 <= i1

  * 1 != i3

----

+---------------------------------+---------------------------------+
|                                 |                                 |
| State: After                    | State: After (Semi-Concrete)    |
|                                 |                                 |
| * Global Variables              | * Global Variables              |
|                                 |                                 |
|   * @@x_arr = KArray#0          |   * @@x_arr = KArray#0          |
|                                 |                                 |
|     * min=i0; length=i1; max=i2 |     * min=-2; length=3; max=0   |
|                                 |                                 |
|     * [1] = i5                  |     * [1] = i5                  |
|                                 |                                 |
|     * [2] = i6                  |     * [2] = i6                  |
|                                 |                                 |
|     * [i3] = i4                 |     * [6] = i4                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 6                     |
|                                 |                                 |
| * Path Conditions               | * Path Conditions               |
|                                 |                                 |
|   * i0 <= i2                    |   * -2 <= 0                     |
|                                 |                                 |
|   * 0 <= i1                     |   * 0 <= 3                      |
|                                 |                                 |
|   * i1 == i2 - i0 + 1           |   * 3 == 0 - -2 + 1             |
|                                 |                                 |
|   * 1 <= i1                     |   * 1 <= 3                      |
|                                 |                                 |
|   * 2 <= i1                     |   * 2 <= 3                      |
|                                 |                                 |
|   * 1 != i3                     |   * 1 != 6                      |
|                                 |                                 |
|   * 3 <= i1                     |   * 3 <= 3                      |
|                                 |                                 |
|   * 2 != i3                     |   * 2 != 6                      |
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
|     * min=i0; length=i1; max=i2 |     * min=-1; length=2; max=0   |
|                                 |                                 |
|     * [2] = i4                  |     * [2] = i4                  |
|                                 |                                 |
|     * [1] = i5                  |     * [1] = i5                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 2                     |
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
|   * 1 != i3                     |   * 1 != 2                      |
|                                 |                                 |
|   * 2 == i3                     |   * 2 == 2                      |
|                                 |                                 |
| * Result: i4                    | * Result: i4                    |
|                                 |                                 |
+---------------------------------+---------------------------------+
