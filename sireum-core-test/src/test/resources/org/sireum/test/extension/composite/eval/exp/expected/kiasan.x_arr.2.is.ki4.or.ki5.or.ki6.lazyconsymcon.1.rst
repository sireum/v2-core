Exp: @@x_arr[2]

State: Before

* Global Variables

  * @@x_arr = KArray#0

    * min=i0; length=i1; max=i2

    * [1] = i4

  * @@y = i3

* Path Conditions

  * i0 <= i2

  * 0 <= i1

  * i1 == i2 - i0 + 1

  * 1 <= i1

  * 1 == i3

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
|     * [1] = i4                  |     * [1] = i4                  |
|                                 |                                 |
|     * [2] = i5                  |     * [2] = i5                  |
|                                 |                                 |
|   * @@y = i3                    |   * @@y = 1                     |
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
|   * 1 == i3                     |   * 1 == 1                      |
|                                 |                                 |
|   * 2 <= i1                     |   * 2 <= 2                      |
|                                 |                                 |
| * Result: i5                    | * Result: i5                    |
|                                 |                                 |
+---------------------------------+---------------------------------+
