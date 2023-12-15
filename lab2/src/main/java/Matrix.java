import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Matrix {
	private int rows;
	private int columns;
	public double[][] matrix;


	Matrix() {
		this.rows = 0;
		this.columns = 0;
		this.matrix = new double[0][0];

	}

	Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.matrix = new double[rows][columns];
	}

	Matrix(Matrix otherMatrix) {
		this.rows = otherMatrix.getRows();
		this.columns = otherMatrix.getColumns();
		this.matrix = new double[rows][columns];
		for (int i = 0; i < rows; i++) {
			System.arraycopy(otherMatrix.matrix[i], 0, this.matrix[i], 0, columns);
		}
	}

	Matrix(double[][] data) {
		this.rows = data.length;
		this.columns = data[0].length;
		this.matrix = new double[rows][columns];
		for (int i = 0; i < rows; i++) {
			System.arraycopy(data[i], 0, this.matrix[i], 0, columns);
		}
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}


	public double get(int row, int column) {
		return matrix[row][column];
	}

	public void set(int row, int column, double value) {
		matrix[row][column] = value;
	}

	public void print() {
		for (int i = 0; i < rows; i++) {
			System.out.print("[ ");
			for (int j = 0; j < columns; j++) {
				System.out.print(matrix[i][j]);
				if (j < columns - 1) {
					System.out.print(", ");
				}
			}
			System.out.println(" ]");
		}
	}
	public void fillAllByRandomValueDouble(int minimum, int maximum) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				this.matrix[i][j] = (Math.random() * (maximum - minimum)) + minimum;
			}
		}
	}

	public void fillAllByRandomValueInt(int minimum, int maximum) {
		Random rn = new Random();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				this.matrix[i][j] = rn.nextInt(maximum - minimum + 1) + minimum;
			}
		}
	}

	public void copyDataFromAnother(double[][] newData) {
		if (newData.length != rows || newData[0].length != columns) {
			throw new IllegalArgumentException("Input array dimensions must match matrix dimensions.");
		}

		for (int i = 0; i < rows; i++) {
			System.arraycopy(newData[i], 0, this.matrix[i], 0, columns);
		}
	}

	public void copyDataFromAnother(Matrix m) {
		if (m.matrix.length != rows || m.matrix[0].length != columns) {
			throw new IllegalArgumentException("Input array dimensions must match matrix dimensions.");
		}

		for (int i = 0; i < rows; i++) {
			System.arraycopy(m.matrix[i], 0, this.matrix[i], 0, columns);
		}
	}


	public void fillByConsoleInput() {
		Scanner scanner = new Scanner(System.in);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print("Enter value for element at row " + (i + 1) + ", column " + (j + 1) + ": ");
				matrix[i][j] = scanner.nextDouble();
			}
		}

	}

	public void changeDefiniteElement(int indexOfRaw, int indexOfColumn, double yourValue) {
		if (indexOfRaw < 0 || indexOfRaw >= rows || indexOfColumn < 0 || indexOfColumn >= columns) {
			throw new IllegalArgumentException("Invalid row or column index.");
		}
		this.matrix[indexOfRaw][indexOfColumn] = yourValue;
	}

	public double getElement(int row, int column) {
		if (row < 0 || row >= rows || column < 0 || column >= columns) {
			throw new IllegalArgumentException("Invalid row or column index.");
		}
		return matrix[row][column];
	}

	public Matrix getRow(int row) {

		row = row - 1;

		if (row < 0 || row > rows) {
			throw new IllegalArgumentException("Invalid row index.");
		}

		Matrix matrixRow = new Matrix(1, columns);

		for (int i = row; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				matrixRow.set(0, j, matrix[row][j]);
			}
		}
		return matrixRow;
	}

	public Matrix getColumn(int column) {

		column = column - 1;

		if (column < 0 || column > columns) {
			throw new IllegalArgumentException("Invalid column index.");
		}

		Matrix matrixColumn = new Matrix(rows, 1);

		for (int i = 0; i < getRows(); i++) {
			for (int j = column; j < getColumns(); j++) {
				matrixColumn.set(i, 0, matrix[i][column]);
			}
		}
		return matrixColumn;
	}


	public static String dimensionMatrix(Matrix m) {
		int row = m.getRows();
		int columns = m.getColumns();
		return row + " x " + columns;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Matrix)) {
			return false;
		}
		Matrix m = (Matrix) o;
		return this.getRows() == m.getRows()
				&& this.getColumns() == m.getColumns()
				&& Arrays.deepEquals(this.matrix, m.matrix);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + getRows();
		result = 31 * result + getColumns();

		for (int i = 0; i < rows; i++) {
			result = 31 * result + Double.hashCode(matrix[i][0]);
		}

		return result;
	}


	public Matrix addMatrices(Matrix mat){

		if (this.matrix.length != mat.matrix.length || this.matrix[0].length != mat.matrix[0].length){
			throw new IllegalArgumentException((""));
		}
		Matrix newMatrix = new Matrix(this);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				newMatrix.matrix[i][j] = this.matrix[i][j] + mat.matrix[i][j];
			}
		}
		return newMatrix;
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

	public static double[][] multiplicationByScalar(double[][] matrixData, double scalar) {
		int rows = matrixData.length;
		int columns = matrixData[0].length;

		double[][] resultMatrixData = new double[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				resultMatrixData[i][j] = matrixData[i][j] * scalar;
			}
		}

		return resultMatrixData;
	}


	public static Matrix multiplicationMatrices(Matrix a, Matrix b) {
		if (a.matrix.length == 0 || b.matrix.length == 0) {
			throw new IllegalArgumentException("No matrices provided for addition.");
		}

		if (a.getColumns() != b.getRows()) {
			throw new IllegalArgumentException("Such matrices cannot be multiplied, since the number of columns of matrix 'a' is not equal to the number of rows of matrix 'b'.");
		}

		Matrix result = new Matrix(a.getRows(), b.getColumns());

		for (int i = 0; i < a.getRows(); i++) {
			for (int j = 0; j < b.getColumns(); j++) {
				double sum = 0;
				for (int k = 0, l = 0; k < a.getColumns() && l < b.getRows(); k++, l++) {
					double number1 = a.matrix[i][k];
					double number2 = b.matrix[l][j];
					double mult = number1 * number2;
					sum = sum + mult;
				}
				result.set(i, j, sum);
			}
		}
		return result;

	}

	public Matrix transposeMatrix() {
		Matrix transposedMatrix = new Matrix(columns, rows);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				transposedMatrix.set(j, i, matrix[i][j]);
			}
		}

		return transposedMatrix;
	}

	public static Matrix createDiagonal(double... m) {

		Matrix result = new Matrix(m.length, m.length);

		for (int i = 0; i < m.length; i++) {
			result.matrix[i][i] = m[i];
		}

		return result;
	}

	public static Matrix createSingle(int dimension) {
		Matrix matrix = new Matrix(dimension, dimension);
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (i == j) {
					matrix.set(i, j, 1);
				} else {
					matrix.set(i, j, 0);

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

	public static Matrix RandomColumn(int row, int minimum, int maximum) {
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


	public Matrix transformToLowerTriangular() {
		int counterOfPermute = 0;
		for (int centalR = 0, centalC = 0; centalR < getRows() && centalC < getColumns(); centalR++, centalC++) {
			if (matrix[centalR][centalC] != 0) {
				for (int i = centalC; i < getRows(); i++) {
					Matrix divideM = getRow(centalR + 1);
					if ((i + 1) < getRows()) {
						divideM = Matrix.multiplicationByScalar(divideM, -1 * matrix[i + 1][centalC] / matrix[centalR][centalC]);
						addRowToMatrix(divideM, i + 1);
					} else {
						break;
					}
				}
			} else {
				for (int j = centalC; j < getColumns(); j++) {
					if ((j + 1) < getColumns()) {
						permuteColumn(j + 1, j + 2);
						counterOfPermute++;
					}
					if (matrix[centalR][centalC] != 0) {
						break;
					}
				}
				if (matrix[centalR][centalC] == 0) {
					break;
				}
				centalR--;
				centalC--;

			}
		}

		for (int centalR = 0, centalC = 0; centalR < getRows() && centalC < getColumns(); centalR++, centalC++) {
			for (int i = centalC; i < getRows() - 1; i++) {
				this.set(i + 1, centalC, Math.round(matrix[i + 1][centalC]));

			}
		}

		if (Math.abs(counterOfPermute) % 2 == 0) {
		} else {
			matrix = Matrix.multiplicationByScalar(matrix, -1);
		}
		return new Matrix(matrix);
	}

	public void addRowToMatrix(Matrix row, int index) {
		if (index < 0 || index >= rows || row.getColumns() != this.getColumns()) {
			throw new IllegalArgumentException("Invalid index or row dimensions.");
		}

		for (int i = 0; i < this.getColumns(); i++) {
			this.matrix[index][i] += row.get(0, i);
		}
	}

	public void permuteColumn(int column1, int column2) {
		Matrix matrixColumn_1 = getColumn(column1);
		Matrix matrixColumn_2 = getColumn(column2);

		for (int j = 0; j < getRows(); j++) {
			matrix[j][column2 - 1] = matrixColumn_1.matrix[j][0];
		}
		for (int j = 0; j < getRows(); j++) {
			matrix[j][column1 - 1] = matrixColumn_2.matrix[j][0];
		}

	}

	public Matrix transformToUpperTriangular(){
		Matrix matrixCopy = new Matrix(matrix);
		int LastDigitOfTriangul;
		if (this.getColumns() < this.getRows()) {
			LastDigitOfTriangul = 0;
		} else {
			LastDigitOfTriangul = this.getColumns() - this.getRows();
		}
		int count_backing = 0;
		for (int i = this.getColumns() - 1; i > LastDigitOfTriangul - 1; i--) {
			count_backing++;
			int row_pivot = this.getRows() - count_backing;

			Boolean matrix_swaped = false;
			for (int j = row_pivot; j > -1; j--) {
				if(matrix_swaped){

				} else {
					if (matrixCopy.matrix[j][i] != 0) {
						double[] temp = matrixCopy.matrix[row_pivot];
						matrixCopy.matrix[row_pivot] = matrixCopy.matrix[j];
						matrixCopy.matrix[j] = temp;
						matrix_swaped = true;
					}
				}

			}

			double first_d = matrixCopy.matrix[row_pivot][i];

			for (int j = row_pivot - 1; j > -1 ; j--) {
				double other_d = matrixCopy.matrix[j][i];
				if(first_d != 0) {
					double alpha = other_d / first_d;
					for (int k = 0; k < this.getColumns(); k++) {
						matrixCopy.matrix[j][k] = matrixCopy.matrix[j][k] - alpha*matrixCopy.matrix[row_pivot][k];
					}
				}
			}
		}
		for (int centalR = 0, centalC = 0; centalR < getRows() && centalC < getColumns(); centalR++, centalC++) {
			for (int i = 0; i<getRows(); i++){
				if (matrixCopy.matrix[i][centalC] == matrixCopy.matrix[centalR][centalC]){
					break;
				}else {
					matrixCopy.matrix[i][centalC] = Math.round(matrixCopy.matrix[i][centalC]);
				}
			}
		}

		return matrixCopy;
	}
}




