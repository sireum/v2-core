package Simple_Sort
is
  Max_Elements : constant := 3;
  
  subtype Index_Type is Integer range 1 .. Max_Elements;
  type Vector is array(Index_Type) of Integer;

  function isSorted(Z : Vector) return Boolean;
  function isPerm(A, B : Vector) return Boolean;
  function isSet(A : Vector) return Boolean;
  
  procedure BubbleSort(A : in out Vector);
  --# derives A from A;
  -- pre isSet(A);
  --# post isSorted(A) and then isPerm(A, A~);

  procedure BubbleSort_using_SwapElements(A : in out Vector);
  --# derives A from A;
  -- pre isSet(A);  
  --# post isSorted(A) and then isPerm(A, A~);
    
  procedure InsertionSort(A : in out Vector);
  --# derives A from A;
  -- pre isSet(A);  
  --# post isSorted(A) and then isPerm(A, A~);
  
  procedure SelectionSort(A : in out Vector);
  --# derives A from A;
  -- pre isSet(A);  
  --# post isSorted(A) and then isPerm(A, A~);
  
  procedure ShellSort(A : in out Vector);
  --# derives A from A;
  -- pre isSet(A);  
  --# post isSorted(A) and then isPerm(A, A~);
    
end Simple_Sort;
