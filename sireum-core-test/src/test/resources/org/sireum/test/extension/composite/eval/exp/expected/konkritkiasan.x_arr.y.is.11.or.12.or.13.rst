Exp: @@x_arr[@@y]

State: Before

* Global Variables

  * @@x_arr = CArray#0

    * min=1; length=3; max=3

    * [1] = 11

    * [2] = 12

    * [3] = 13

  * @@y = i1

----

+------------------------------+------------------------------+
|                              |                              |
| State: After                 | State: After (Semi-Concrete) |
|                              |                              |
| * Global Variables           | * Global Variables           |
|                              |                              |
|   * @@x_arr = CArray#0       |   * @@x_arr = CArray#0       |
|                              |                              |
|     * min=1; length=3; max=3 |     * min=1; length=3; max=3 |
|                              |                              |
|     * [1] = 11               |     * [1] = 11               |
|                              |                              |
|     * [2] = 12               |     * [2] = 12               |
|                              |                              |
|     * [3] = 13               |     * [3] = 13               |
|                              |                              |
|   * @@y = i1                 |   * @@y = 1                  |
|                              |                              |
| * Path Conditions            | * Path Conditions            |
|                              |                              |
|   * 1 == i1                  |   * 1 == 1                   |
|                              |                              |
| * Result: 11                 | * Result: 11                 |
|                              |                              |
+------------------------------+------------------------------+

----

+------------------------------+------------------------------+
|                              |                              |
| State: After                 | State: After (Semi-Concrete) |
|                              |                              |
| * Global Variables           | * Global Variables           |
|                              |                              |
|   * @@x_arr = CArray#0       |   * @@x_arr = CArray#0       |
|                              |                              |
|     * min=1; length=3; max=3 |     * min=1; length=3; max=3 |
|                              |                              |
|     * [1] = 11               |     * [1] = 11               |
|                              |                              |
|     * [2] = 12               |     * [2] = 12               |
|                              |                              |
|     * [3] = 13               |     * [3] = 13               |
|                              |                              |
|   * @@y = i1                 |   * @@y = 2                  |
|                              |                              |
| * Path Conditions            | * Path Conditions            |
|                              |                              |
|   * 2 == i1                  |   * 2 == 2                   |
|                              |                              |
| * Result: 12                 | * Result: 12                 |
|                              |                              |
+------------------------------+------------------------------+

----

+------------------------------+------------------------------+
|                              |                              |
| State: After                 | State: After (Semi-Concrete) |
|                              |                              |
| * Global Variables           | * Global Variables           |
|                              |                              |
|   * @@x_arr = CArray#0       |   * @@x_arr = CArray#0       |
|                              |                              |
|     * min=1; length=3; max=3 |     * min=1; length=3; max=3 |
|                              |                              |
|     * [1] = 11               |     * [1] = 11               |
|                              |                              |
|     * [2] = 12               |     * [2] = 12               |
|                              |                              |
|     * [3] = 13               |     * [3] = 13               |
|                              |                              |
|   * @@y = i1                 |   * @@y = 3                  |
|                              |                              |
| * Path Conditions            | * Path Conditions            |
|                              |                              |
|   * 3 == i1                  |   * 3 == 3                   |
|                              |                              |
| * Result: 13                 | * Result: 13                 |
|                              |                              |
+------------------------------+------------------------------+
