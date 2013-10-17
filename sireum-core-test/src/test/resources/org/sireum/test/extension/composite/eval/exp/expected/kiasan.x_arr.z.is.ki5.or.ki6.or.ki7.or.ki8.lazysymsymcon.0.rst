Exp: @@x_arr[@@z]

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
|     * min=i1; length=i2; max=i3 |     * min=0; length=3; max=2    |
|                                 |                                 |
|     * [1] = i6                  |     * [1] = i6                  |
|                                 |                                 |
|     * [i4] = i5                 |     * [5] = i5                  |
|                                 |                                 |
|     * [i7] = i8                 |     * [6] = i8                  |
|                                 |                                 |
|   * @@y = i4                    |   * @@y = 5                     |
|                                 |                                 |
|   * @@z = i7                    |   * @@z = 6                     |
|                                 |                                 |
| * Path Conditions               | * Path Conditions               |
|                                 |                                 |
|   * i1 <= i3                    |   * 0 <= 2                      |
|                                 |                                 |
|   * 0 <= i2                     |   * 0 <= 3                      |
|                                 |                                 |
|   * i2 == i3 - i1 + 1           |   * 3 == 2 - 0 + 1              |
|                                 |                                 |
|   * 1 <= i2                     |   * 1 <= 3                      |
|                                 |                                 |
|   * 2 <= i2                     |   * 2 <= 3                      |
|                                 |                                 |
|   * 1 != i4                     |   * 1 != 5                      |
|                                 |                                 |
|   * 3 <= i2                     |   * 3 <= 3                      |
|                                 |                                 |
|   * i7 != 1                     |   * 6 != 1                      |
|                                 |                                 |
|   * i7 != i4                    |   * 6 != 5                      |
|                                 |                                 |
| * Result: i8                    | * Result: i8                    |
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
|     * min=i1; length=i2; max=i3 |     * min=0; length=2; max=1    |
|                                 |                                 |
|     * [1] = i6                  |     * [1] = i6                  |
|                                 |                                 |
|     * [i4] = i5                 |     * [5] = i5                  |
|                                 |                                 |
|   * @@y = i4                    |   * @@y = 5                     |
|                                 |                                 |
|   * @@z = i7                    |   * @@z = 1                     |
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
|   * 1 != i4                     |   * 1 != 5                      |
|                                 |                                 |
|   * i7 == 1                     |   * 1 == 1                      |
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
|     * min=i1; length=i2; max=i3 |     * min=0; length=2; max=1    |
|                                 |                                 |
|     * [1] = i6                  |     * [1] = i6                  |
|                                 |                                 |
|     * [i4] = i5                 |     * [4] = i5                  |
|                                 |                                 |
|   * @@y = i4                    |   * @@y = 4                     |
|                                 |                                 |
|   * @@z = i7                    |   * @@z = 4                     |
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
|   * 1 != i4                     |   * 1 != 4                      |
|                                 |                                 |
|   * i7 == i4                    |   * 4 == 4                      |
|                                 |                                 |
| * Result: i5                    | * Result: i5                    |
|                                 |                                 |
+---------------------------------+---------------------------------+
