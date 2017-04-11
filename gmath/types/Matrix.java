package gmath.types;

import java.util.Arrays;

import gcore.util.ArrayUtils;

public class Matrix<R extends Ring<R>> extends Ring<Matrix<R>> {

	private final R[][] entries;

	private final Class<?> clazz;

	private final int rows;
	private final int columns;

	private R determinant = null;

	@SafeVarargs
	public Matrix(R[]... rows) {

		// set rows to the number of rows
		this.rows = rows.length;

		// set the number of columns to the maximum column length in the given
		// array
		int numOfColumns = 0;
		for (int i = 0; i < rows.length; i++) {
			numOfColumns = Math.max(numOfColumns, rows[i].length);
		}
		this.columns = numOfColumns;

		// if no rows or columns throw an illegal argument exception as you
		// cannot have a 0xn or nx0 matrix
		if (this.rows == 0 || this.columns == 0)
			throw new IllegalArgumentException("Cannot have a 0xn or nx0 matrix!");

		// set the class of the array
		clazz = rows.getClass().getComponentType().getComponentType();

		// copy the given array into an array that is square and has the correct
		// number of terms.
		R[][] copy = copyAndFill2DArray(rows, this.rows, this.columns);

		// set the entries of the matrix to the copy array
		this.entries = copy;
	}

	/**
	 * added constructor for passing the whole array copy process, this
	 * constructor should only be used by inside methods to the class that know
	 * the data is valid so it doesn't need to be checked.
	 * 
	 * @param entries
	 *            entries for the matrix
	 * @param rows
	 *            rows of the matrix
	 * @param columns
	 *            columns of the matrix
	 */
	private Matrix(R[][] entries, int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.entries = entries;
		clazz = entries.getClass().getComponentType().getComponentType();
	}

	@Override
	public Matrix<R> add(Matrix<R> add) {
		// make sure the number of rows and columns match up
		if (!(this.columns == add.columns && this.rows == add.rows))
			throw new IllegalArgumentException("You can only add two matricies if they have the same dimension!");

		// copy the entries of the current matrix
		R[][] entries = copyAndFill2DArray(this.entries, rows, columns);

		// loop through each value of the add matrix and add its entry to the
		// entries array
		for (int i = 0; i < add.rows; i++) {
			for (int j = 0; j < add.columns; j++) {
				entries[i][j] = entries[i][j].add(add.entries[i][j]);
			}
		}

		// return a matrix with the entries array.
		return new Matrix<>(entries, rows, columns);
	}

	@Override
	public Matrix<R> multiply(Matrix<R> mult) {

		// make sure the dimensions of the matrices are compatible for
		// multiplication.
		if (this.columns != mult.rows)
			throw new IllegalArgumentException("You can only multiply an n x r matrix by an r x m matrix!");

		// get an empty matrix to put the values of the multiplication
		R[][] results = copyAndFill2DArray(mult.ZERO().entries, this.rows, mult.columns);

		// get the dot products of each of the rows and columns and store it in
		// the respective places in the matrix
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < mult.columns; j++) {
				results[i][j] = this.getRow(i).dotProduct(mult.getColumn(j));
			}
		}

		// return the resulting matrix
		return new Matrix<>(results, this.rows, mult.columns);
	}

	/**
	 * scales each term of the matrix by the given scalar
	 * 
	 * @param scalar
	 *            number to multiply by
	 * @return scaled matrix
	 */
	public Matrix<R> scale(R scalar) {
		R[][] results = ArrayUtils.copy2DArray(entries, rows, columns);

		// loop through an multiply each term by the scalar
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				results[i][j] = results[i][j].multiply(scalar);
			}
		}

		// return the resulting matrix
		return new Matrix<>(results, rows, columns);
	}

	/**
	 * transposes the current matrix
	 * 
	 * @return current matrix's transposition.
	 */
	public Matrix<R> transpose() {

		// copy the current array to make an array
		R[][] results = ArrayUtils.copy2DArray(entries, columns, rows);

		// loop through and put the entries in the correct position.
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				results[j][i] = entries[i][j];
			}
		}

		// return the matrix
		return new Matrix<>(results, columns, rows);
	}

	@Override
	public Matrix<R> negate() {
		// copy the entries of the current matrix
		R[][] entries = copyAndFill2DArray(this.entries, rows, columns);

		// loop through each value of the matrix and negate it
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				entries[i][j] = entries[i][j].negate();
			}
		}

		return new Matrix<>(entries, rows, columns);
	}

	/**
	 * calculates and returns the determinant, if the determinant of a matrix
	 * has already been computed it is saved for future retreival.
	 * 
	 * @return determinant of the current matrix if it is n x n otherwise
	 *         illegal argument exception is thrown
	 */
	public R determinant() {
		// if the determinant already exists return it
		if (determinant != null)
			return determinant;

		// check that the matrix is square
		if (rows != columns)
			throw new IllegalArgumentException("Matrix must be square to find the determinant!");

		// check if the matrix is 2 x 2
		if (rows == 2) {
			// if so return a * d - b * c and save the determinant value
			determinant = entries[0][0].multiply(entries[1][1]).subtract(entries[0][1].multiply(entries[1][0]));
			return determinant;
		}

		// if the matrix isn't 2x2 take the top row and multiply by the smaller
		// matrices determinants below
		determinant = entries[0][0].ZERO();
		for (int i = 0; i < columns; i++) {
			// add the determinants of each of the individual matricies times
			// the element on the top.
			
			R multiply = entries[0][i];
			
			// if it is an odd column negate the multiplier, this is since the first column is zero
			if (i % 2 == 1)
				multiply = multiply.negate();
			
			// get the exclusion matrix for the next derivative
			Matrix<R> smaller = getExclusionMatrix(0, i);
			
			// add to determinant the multiplier times the determinant of the smaller matrix
			determinant = determinant.add(multiply.multiply(smaller.determinant()));
		}

		// return the determinant
		return determinant;
	}

	/**
	 * defaults to the RIGHTZERO matrix
	 */
	@Override
	public Matrix<R> ZERO() {
		return RIGHTZERO();
	}

	/**
	 * square matrix with all zeros if original matrix has dimension n x m this
	 * will have dimension n x n
	 * 
	 * @return left zero matrix
	 */
	public Matrix<R> LEFTZERO() {
		// fill a matrix with the zero element
		R[] row = Arrays.copyOf(this.entries[0], this.rows);
		Arrays.fill(row, row[0].ZERO());
		R[][] entries = Arrays.copyOf(this.entries, this.rows);

		// fill the array with a new copy for each row
		for (int i = 0; i < entries.length; i++) {
			entries[i] = Arrays.copyOf(row, row.length);
		}

		return new Matrix<>(entries, rows, rows);
	}

	/**
	 * square matrix with all zeros if original matrix has dimension n x m this
	 * will have dimension m x m
	 * 
	 * @return right zero matrix
	 */
	public Matrix<R> RIGHTZERO() {
		// fill a matrix with the zero element
		R[] row = Arrays.copyOf(this.entries[0], this.columns);
		Arrays.fill(row, row[0].ZERO());
		R[][] entries = Arrays.copyOf(this.entries, this.columns);

		// fill the array with a new copy for each row
		for (int i = 0; i < entries.length; i++) {
			entries[i] = Arrays.copyOf(row, row.length);
		}

		return new Matrix<>(entries, columns, columns);
	}

	/**
	 * defaults to RIGHTIDENTITY matrix.
	 */
	@Override
	public Matrix<R> IDENTITY() {
		return RIGHTIDENTITY();
	}

	/**
	 * square matrix with all zeros except along the diagonal, if original
	 * matrix has dimension n x m this will have dimension n x n
	 * 
	 * @return left identity matrix
	 */
	public Matrix<R> LEFTIDENTITY() {
		// get the zero matrix and change it so it has 1's on the diagonal.
		Matrix<R> zero = LEFTZERO();
		for (int i = 0; i < rows; i++) {
			zero.entries[i][i] = zero.entries[0][0].IDENTITY();
		}
		return zero;
	}

	/**
	 * square matrix with all zeros except along the diagonal, if original
	 * matrix has dimension n x m this will have dimension m x m
	 * 
	 * @return right identity matrix
	 */
	public Matrix<R> RIGHTIDENTITY() {
		// get the zero matrix and change it so it has 1's on the diagonal.
		Matrix<R> zero = RIGHTZERO();
		for (int i = 0; i < columns; i++) {
			zero.entries[i][i] = zero.entries[0][0].IDENTITY();
		}
		return zero;
	}

	/**
	 * returns the ith row of the matrix in the form of an nvector
	 * 
	 * @param i
	 *            row to return
	 * @return ith row of the matrix null if out of bounds.
	 */
	public NVector<R> getRow(int i) {
		// check for out of bounds
		if (i < 0 || i >= rows)
			return null;

		return new NVector<>(entries[i]);
	}

	/**
	 * returns the ith column of the matrix in the form of an NVector
	 * 
	 * @param i
	 *            column to return
	 * @return ith column of the matrix null if out of bounds.
	 */
	public NVector<R> getColumn(int i) {
		// check for out of bounds
		if (i < 0 || i >= columns)
			return null;

		// get an empty array of the desired length
		R[] results = Arrays.copyOf(entries[0], rows);

		// replace all of the values with the ith column in entries
		for (int j = 0; j < rows; j++) {
			results[j] = entries[j][i];
		}

		return new NVector<>(results);
	}

	/**
	 * gets the matrix within the current matrix that starts at the start
	 * coordinates and ends at the end coordinates, the first coordinate of the
	 * matrix is 0
	 * 
	 * @param rowStart
	 *            row to start from
	 * @param columnStart
	 *            column to start from
	 * @param rowEnd
	 *            row to end at
	 * @param columnEnd
	 *            column to end at
	 * @return matrix that is made from the sub area.
	 */
	public Matrix<R> getSubMatrix(int rowStart, int columnStart, int rowEnd, int columnEnd) {
		// check for out of bounds and throw illegal argument exception if they
		// are.
		if (rowStart < 0 || columnStart < 0 || rowStart >= rowEnd || columnStart >= columnEnd || rowEnd >= columns
				|| columnEnd >= rows)
			throw new IllegalArgumentException("Invalid coordinates for sub matrix operation!");

		// get the results matrix of the appropriate size
		R[][] results = ArrayUtils.copy2DArray(entries, rowEnd - rowStart, columnEnd - columnStart);

		// copy in the elements from the matrix
		for (int i = 0; i < results.length; i++) {
			for (int j = 0; j < results[i].length; j++) {
				results[i][j] = entries[i + rowStart][j + columnStart];
			}
		}

		// return the resulting matrix
		return new Matrix<>(results, rowEnd - rowStart, columnEnd - columnStart);
	}

	public Matrix<R> getExclusionMatrix(int rowExclude, int columnExclude) {
		// check for out of bounds
		if (rowExclude < 0 || columnExclude < 0 || rowExclude >= rows || columnExclude >= columns)
			throw new IllegalArgumentException("invalid row/column for exclusion matrix!");

		// make a copy of the resulting array
		R[][] results = ArrayUtils.copy2DArray(entries, rows - 1, columns - 1);

		// start at the excluded row and column and add 1 to the entries
		// position.
		for (int i = 0; i < rows - 1; i++) {
			for (int j = 0; j < columns - 1; j++) {
				if (i < rowExclude && j < columnExclude)
					continue;
				
				int rowMove = i >= rowExclude ? 1 : 0;
				int columnMove = j >= columnExclude ? 1 : 0;
				results[i][j] = entries[i + rowMove][j + columnMove];
			}
		}

		// return the resulting matrix
		return new Matrix<>(results, rows - 1, columns - 1);
	}

	@Override
	public boolean equals(Matrix<R> compare) {
		// check for type mismatch
		if (!compare.clazz.equals(this.clazz))
			return false;

		// check for dimension mismatch
		if (!(compare.rows == this.rows && compare.columns == this.columns))
			return false;

		// check for entry mismatch
		for (int i = 0; i < rows; i++) {
			if (!Arrays.equals(this.entries[i], compare.entries[i]))
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int results = 0;
		int prime = 0;
		for (R[] row : entries) {
			results = results * prime + Arrays.hashCode(row);
		}
		return results;
	}

	@Override
	public String toString() {
		String results = "{";
		for (R[] row : entries) {
			results += Arrays.toString(row) + "\n";
		}
		return results.substring(0, results.length() - 1) + "}";
	}

	/**
	 * helper function that takes a rugged array and copies it into a new array
	 * of the corresponding xLength and yLength where the maximum entry of the
	 * new array T will be T[xlength-1][ylength-1].
	 * 
	 * @param original
	 *            matrix to copy
	 * @param xlength
	 *            x length of the new matrix
	 * @param ylength
	 *            y length of the new matrix
	 * @return copied array padded with the zero element of the ring T.
	 */
	private static <T extends Ring<T>> T[][] copyAndFill2DArray(T[][] original, int xlength, int ylength) {

		// copy the given array into an array that is square and has the correct
		// number of terms.
		T[][] copy = ArrayUtils.copy2DArray(original, xlength, ylength);

		// loop through and replace any nulls with zero element.
		for (int i = 0; i < xlength; i++) {
			for (int j = 0; j < ylength; j++) {
				// if null replace with zero
				if (copy[i][j] == null) {
					copy[i][j] = copy[0][0].ZERO();
				}
			}
		}

		return copy;
	}

}
