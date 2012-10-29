package MyMatrix
is
  type Matrix_Index is limited private;
  type Matrix is limited private;
  
  procedure Multiply(X, Y : in     Matrix;
                     Z    :    out Matrix);
  --# derives Z from X, Y;

private
  type Matrix_Index is range 0..9;
  type Matrix is array (Matrix_Index, Matrix_Index) of Integer;
end MyMatrix;

package body MyMatrix
is

  procedure Multiply(X, Y : in     Matrix;
                     Z    :    out Matrix)
  is
  begin
    Z := Matrix'(
      Matrix_Index => (Matrix_Index => 0, others => 0),
      others => (others => 0)
    );
    for I in Matrix_Index loop
      for J in Matrix_Index loop
        for K in Matrix_Index loop
          Z(I, J) := Z(I, J) + X(I, K) * Y(K, J);
        end loop;
      end loop;
    end loop;
  end Multiply;

end MyMatrix;
