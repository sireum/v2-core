Exp: @@x_arr[@@z]

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
|     * min=i0; length=i1; max=i2 |     * min=0; length=3; max=2    |
|                                 |                                 |
|     * [1] = i5                  |     * [1] = i5                  |
|                                 |                                 |
|     * [i3] = i4                 |     * [5] = i4                  |
|                                 |                                 |
|     * [i6] = i7                 |     * [6] = i7                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 5                     |
|                                 |                                 |
|   * @@z = i6                    |   * @@z = 6                     |
|                                 |                                 |
| * Path Conditions               | * Path Conditions               |
|                                 |                                 |
|   * i0 <= i2                    |   * 0 <= 2                      |
|                                 |                                 |
|   * 0 <= i1                     |   * 0 <= 3                      |
|                                 |                                 |
|   * i1 == i2 - i0 + 1           |   * 3 == 2 - 0 + 1              |
|                                 |                                 |
|   * 1 <= i1                     |   * 1 <= 3                      |
|                                 |                                 |
|   * 2 <= i1                     |   * 2 <= 3                      |
|                                 |                                 |
|   * 1 != i3                     |   * 1 != 5                      |
|                                 |                                 |
|   * 3 <= i1                     |   * 3 <= 3                      |
|                                 |                                 |
|   * i6 != 1                     |   * 6 != 1                      |
|                                 |                                 |
|   * i6 != i3                    |   * 6 != 5                      |
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
|     * min=i0; length=i1; max=i2 |     * min=0; length=2; max=1    |
|                                 |                                 |
|     * [1] = i5                  |     * [1] = i5                  |
|                                 |                                 |
|     * [i3] = i4                 |     * [5] = i4                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 5                     |
|                                 |                                 |
|   * @@z = i6                    |   * @@z = 1                     |
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
|   * 1 != i3                     |   * 1 != 5                      |
|                                 |                                 |
|   * i6 == 1                     |   * 1 == 1                      |
|                                 |                                 |
| * Result: i5                    | * Result: i5                    |
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
|     * min=i0; length=i1; max=i2 |     * min=0; length=2; max=1    |
|                                 |                                 |
|     * [1] = i5                  |     * [1] = i5                  |
|                                 |                                 |
|     * [i3] = i4                 |     * [4] = i4                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 4                     |
|                                 |                                 |
|   * @@z = i6                    |   * @@z = 4                     |
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
|   * 1 != i3                     |   * 1 != 4                      |
|                                 |                                 |
|   * i6 == i3                    |   * 4 == 4                      |
|                                 |                                 |
| * Result: i4                    | * Result: i4                    |
|                                 |                                 |
+---------------------------------+---------------------------------+
