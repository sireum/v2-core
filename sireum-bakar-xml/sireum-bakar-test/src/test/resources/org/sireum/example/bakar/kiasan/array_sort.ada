with Array_Utils;
--# inherit Array_Utils;
package Array_Sort
is
  subtype Index is Integer range 1 .. 3;
  subtype SubVector is Array_Utils.Vector(Index);
  
  procedure BubbleSort(A : in out SubVector);
  --# derives A from A;
  
  procedure BubbleSort_using_SwapElements(A : in out SubVector);
  --# derives A from A;
  
  procedure InsertionSort(A : in out SubVector);
  --# derives A from A;
  
  procedure SelectionSort(A : in out SubVector);
  --# derives A from A;

end Array_Sort;

package body Array_Sort is
  
  procedure BubbleSort(A : in out SubVector) is
  begin
    Array_Utils.Util_BubbleSort(A);
  end BubbleSort;
  
  procedure BubbleSort_using_SwapElements(A : in out SubVector) is
  begin
    Array_Utils.Util_BubbleSort_using_SwapElements(A);
  end BubbleSort_using_SwapElements;
  
  procedure InsertionSort(A : in out SubVector) is
  begin
    Array_Utils.Util_InsertionSort(A);
  end InsertionSort;
  
  procedure SelectionSort(A : in out SubVector) is
  begin
    Array_Utils.Util_SelectionSort(A);
  end SelectionSort;
    
  function test1 return SubVector is
    Ret : SubVector := SubVector'(2 => 3, 1 => -1, 3 => 0);  
  begin
    Array_Utils.Util_BubbleSort(Ret);
    return Ret;
  end test1;
  
end Array_Sort;
