import java.util.Random;

public interface IMatrix
{
	public void set(int row, int column, double value);

	public void print();

	public void fillAllByRandomValueDouble(int minimum, int maximum);

	public void fillAllByRandomValueInt(int minimum, int maximum);

	public void copyDataFromAnother(double[][] newData);

	public void copyDataFromAnother(Matrix m);

	public void fillByConsoleInput();

	public void changeDefiniteElement(int indexOfRaw, int indexOfColumn, double yourValue);

	public Matrix getRow(int row);

	public Matrix getColumn(int column);

	public double getElement(int row, int column);

	public static Matrix RandomColumn(int row,int minimum, int maximum) {
		Random rn = new Random();
		if (row < 0) {
			throw new IllegalArgumentException();
		}

		Matrix result = new Matrix(row, 1);

		for (int r = 0; r < row; r++) {
			result.matrix[r][0] = rn.nextInt(maximum - minimum + 1) + minimum;
		}
		return result;
	}

	public static Matrix addMatrices(Matrix ... matrices){
		if (matrices.length ==0){
			throw new IllegalArgumentException("No matrices provided for addition.");
		}

		int rows=matrices[0].getRows();
		int columns = matrices[0].getColumns();

		for (int i=1; i < matrices.length; i++) {
			if (matrices[i].getRows() != rows || matrices[i].getColumns() != columns) {
				throw new IllegalArgumentException("Matrix dimensions must match for addition.");
			}
		}
		Matrix result = new Matrix(rows, columns);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				double sum = 0.0;
				for (Matrix matrix : matrices) {
					sum += matrix.get(i, j);
				}
				result.set(i, j, sum);
			}
		}
		return result;
	}

	public static Matrix multiplicationByScalar(Matrix a, double scalar) {
		Matrix result = new Matrix(a.getRows(), a.getColumns());
		for (int i = 0; i < a.getRows(); i++) {
			for (int j = 0; j < a.getColumns(); j++) {
				result.set(i, j, a.get(i, j) * scalar);
			}
		}
		return result;
	}

	public static Matrix multiplicationMatrices(Matrix a, Matrix b){
		if (a.matrix.length ==0 || b.matrix.length==0){
			throw new IllegalArgumentException("No matrices provided for addition.");
		}

		if (a.getColumns()!=b.getRows()){
			throw new IllegalArgumentException("Such matrices cannot be multiplied, since the number of columns of matrix 'a' is not equal to the number of rows of matrix 'b'.");
		}

		Matrix result = new Matrix(a.getRows(), b.getColumns());

		for (int i = 0; i < a.getRows(); i++) {
			for (int j = 0; j < b.getColumns(); j++) {
				double sum=0;
				for (int k = 0, l = 0; k < a.getColumns() && l<b.getRows(); k++,l++) {
					double number1 = a.matrix[i][k];
					double number2 = b.matrix[l][j];
					double mult = number1*number2;
					sum=sum+mult;
				}
				result.set(i, j,sum);
			}
		}
		return result;

	}

	public Matrix transposeMatrix();

	public static Matrix createDiagonal(double... m) {

		Matrix result = new Matrix(m.length, m.length);

		for (int i = 0; i < m.length; i++) {
			result.matrix[i][i] = m[i];
		}

		return result;
	}

	public static Matrix createSingle(int dimension){
		Matrix matrix = new Matrix(dimension,dimension);
		for (int i=0; i<dimension; i++){
			for (int j=0; j<dimension; j++){
				if(i==j){
					matrix.set(i,j,1);
				}else {
					matrix.set(i,j,0);

				}
			}
		}
		return matrix;
	}

	public static Matrix RandomRow(int column, int minimum, int maximum) {
		Random rn = new Random();

		if (column < 0) {
			throw new IllegalArgumentException();
		}

		Matrix result = new Matrix(1, column);

		for (int c = 0; c < column; c++) {
			result.matrix[0][c] = rn.nextInt(maximum - minimum + 1) + minimum;
		}
		return result;
	}

	public Matrix transformToLowerTriangular();

	public Matrix transformToUpperTriangular();

}
