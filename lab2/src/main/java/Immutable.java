public class Immutable extends Matrix
{

	Immutable(Matrix otherMatrix) {
		super(otherMatrix);
	}


	@Override
	public void set(int row, int column, double value) {
		throw new IllegalArgumentException("You cannot change the Immutable matrix");
	}

	@Override
	public Matrix addMatrices(Matrix matrix) {
		return new Matrix(this).addMatrices(matrix);
	}


	@Override
	public void fillAllByRandomValueDouble(int minimum, int maximum) {
		throw new IllegalArgumentException("You cannot change the Immutable matrix");
	}

	@Override
	public void fillAllByRandomValueInt(int minimum, int maximum) {
		throw new IllegalArgumentException("You cannot change the Immutable matrix");

	}

	@Override
	public void copyDataFromAnother(double[][] newData) {
		throw new IllegalArgumentException("You cannot change the Immutable matrix");
	}

	@Override
	public void copyDataFromAnother(Matrix m) {
		throw new IllegalArgumentException("You cannot change the Immutable matrix");
	}

	@Override
	public void fillByConsoleInput() {
		throw new IllegalArgumentException("You cannot change the Immutable matrix");
	}

	@Override
	public void changeDefiniteElement(int indexOfRaw, int indexOfColumn, double yourValue) {
		throw new IllegalArgumentException("You cannot change the Immutable matrix");
	}



	@Override
	public Matrix getRow(int row) {

		row = row - 1;

		if (row < 0 || row > getRows()) {
			throw new IllegalArgumentException("Invalid row index.");
		}

		Matrix matrixRow = new Matrix(1, getColumns());

		for (int i = row; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				matrixRow.set(0, j, matrix[row][j]);
			}
		}
		return matrixRow;
	}

	public Matrix getColumn(int column) {

		column = column - 1;

		if (column < 0 || column > getColumns()) {
			throw new IllegalArgumentException("Invalid column index.");
		}

		Matrix matrixColumn = new Matrix(getRows(), 1);

		for (int i = 0; i < getRows(); i++) {
			for (int j = column; j < getColumns(); j++) {
				matrixColumn.set(i, 0, matrix[i][column]);
			}
		}
		return matrixColumn;
	}

	public static String dimensionMatrix(Immutable m){
		int row = m.getRows();
		int columns = m.getColumns();
		return row + " x " + columns;
	}
	@Override
	public Matrix transposeMatrix() {
		Matrix transposedMatrix = new Matrix(getRows(), getColumns());

		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				transposedMatrix.set(j, i, matrix[i][j]);
			}
		}

		return transposedMatrix;
	}

	@Override
	public Matrix transformToLowerTriangular() {
		Matrix matrixCopy = new Matrix(matrix);
		int counterOfPermute = 0;
		for (int centalR = 0, centalC = 0; centalR < getRows() && centalC < getColumns(); centalR++, centalC++) {
			if (matrixCopy.matrix[centalR][centalC] != 0) {
				for (int i = centalC; i < getRows(); i++) {
					Matrix divideM = matrixCopy.getRow(centalR + 1);
					if ((i + 1) < getRows()) {
						divideM = Matrix.multiplicationByScalar(divideM, -1 * matrixCopy.matrix[i + 1][centalC] / matrixCopy.matrix[centalR][centalC]);
						matrixCopy.addRowToMatrix(divideM, i + 1);
					} else {
						break;
					}
				}
			} else {
				for (int j = centalC; j < getColumns(); j++) {
					if ((j + 1) < getColumns()) {
						matrixCopy.permuteColumn(j + 1, j + 2);
						counterOfPermute++;
					}
					if (matrixCopy.matrix[centalR][centalC] != 0) {
						break;
					}
				}
				if (matrixCopy.matrix[centalR][centalC] == 0) {
					return matrixCopy;
				}
				centalR--;
				centalC--;

			}
		}

		for (int centalR = 0, centalC = 0; centalR < getRows() && centalC < getColumns(); centalR++, centalC++) {
			for (int i = centalC; i < getRows() - 1; i++) {
				matrixCopy.set(i + 1, centalC, Math.round(matrixCopy.matrix[i + 1][centalC]));

			}
		}

		if (Math.abs(counterOfPermute) % 2 == 0) {
			return matrixCopy;
		} else {
			return Matrix.multiplicationByScalar(matrixCopy, -1);
		}
	}


	public void addRowToMatrix(Matrix row, int index) {
		if (index < 0 || index >= getRows() || row.getColumns() != this.getColumns()) {
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

	@Override
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
