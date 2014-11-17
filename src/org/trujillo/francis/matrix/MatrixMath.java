package org.trujillo.francis.matrix;

public class MatrixMath {

    /**
     * This class a matrix utility class and cannot be instantiated.
     */
    private MatrixMath() {
    }

    /**
     * Transpose of a matrix - Swap the columns with rows
     *
     * @param matrix
     * @return
     */
    public static Matrix transpose(Matrix matrix) {
        Matrix transposedMatrix = new Matrix(matrix.getNcols(), matrix.getNrows());
        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols(); j++) {
                transposedMatrix.setValueAt(j, i, matrix.getValueAt(i, j));
            }
        }
        return transposedMatrix;
    }

    /**
     * Inverse of a matrix - A-1 * A = I where I is the identity matrix A matrix
     * that have inverse is called non-singular or invertible. If the matrix
     * does not have inverse it is called singular. For a singular matrix the
     * values of the inverted matrix are either NAN or Infinity Only square
     * matrices have inverse and the following method will throw exception if
     * the matrix is not square.
     *
     * @param matrix
     * @return
     * @throws NoSquareException
     * @throws org.trujillo.francis.matrix.IllegalDimensionException
     */
    public static Matrix inverse(Matrix matrix) throws NoSquareException, IllegalDimensionException {
        return (transpose(cofactor(matrix)).scalarMultiplication(1.0 / determinant(matrix)));
    }

    /**
     * Determinant of a square matrix The following function find the
     * determinant in a recursively.
     * 
     * In linear algebra, the determinant is a value associated with a square matrix.
     * 
     * In the first case the system has a unique solution exactly when the determinant is nonzero; 
     * when the determinant is zero there are either no solutions or many solutions
     * 
     * In the second case the transformation has an inverse operation exactly when the determinant is nonzero.
     * 
     * The determinant of the identity matrix is 1.
     *
     * @param matrix
     * @return
     * @throws NoSquareException
     * @throws org.trujillo.francis.matrix.IllegalDimensionException
     */
    public static double determinant(Matrix matrix) throws NoSquareException, IllegalDimensionException {
        if (!matrix.isSquare()) {
            throw new NoSquareException("matrix need to be square.");
        }
        if (matrix.size() == 1) {
            return matrix.getValueAt(0, 0);
        }

        if (matrix.size() == 2) {
            // | a  b |
            // | c  d |
            //
            // ad -bc
            return (matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - (matrix.getValueAt(0, 1) * matrix.getValueAt(1, 0));
        }
        double sum = 0.0;
        for (int i = 0; i < matrix.getNcols(); i++) {
            sum += changeSign(i) * matrix.getValueAt(0, i) * determinant(createSubMatrix(matrix, 0, i));
        }
        return sum;
    }

    /**
     * Determine the sign; i.e. even numbers have sign + and odds -
     *
     * @param i
     * @return
     */
    private static int changeSign(int i) {
        if (i % 2 == 0) {
            return 1;
        }
        return -1;
    }

    /**
     * Creates a submatrix excluding the given row and column
     *
     * @param matrix
     * @param excluding_row
     * @param excluding_col
     * @return
     */
    public static Matrix createSubMatrix(Matrix matrix, int excluding_row, int excluding_col) throws IllegalDimensionException {
        if(excluding_row > matrix.getNrows() -1) {
            String msg = "You specified to exclude a row # " + excluding_row + " and index was out of bounds.";
            throw new IllegalDimensionException(msg);
        }
        
        Matrix mat = new Matrix(matrix.getNrows() - 1, matrix.getNcols() - 1);
        int r = -1;
        for (int i = 0; i < matrix.getNrows(); i++) {
            if (i == excluding_row) {
                continue;
            }
            r++;
            int c = -1;
            for (int j = 0; j < matrix.getNcols(); j++) {
                if (j == excluding_col) {
                    continue;
                }
                mat.setValueAt(r, ++c, matrix.getValueAt(i, j));
            }
        }
        return mat;
    }

    /**
     * The cofactor of a matrix
     *
     * @param matrix
     * @return
     * @throws NoSquareException
     * @throws org.trujillo.francis.matrix.IllegalDimensionException
     */
    public static Matrix cofactor(Matrix matrix) throws NoSquareException, IllegalDimensionException {
        Matrix mat = new Matrix(matrix.getNrows(), matrix.getNcols());
        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols(); j++) {
                mat.setValueAt(i, j, changeSign(i) * changeSign(j) * determinant(createSubMatrix(matrix, i, j)));
            }
        }

        return mat;
    }

    /**
     * Adds two matrices of the same dimension
     *
     * @param matrix1
     * @param matrix2
     * @return
     * @throws IllegalDimensionException
     */
    public static Matrix add(Matrix matrix1, Matrix matrix2) throws IllegalDimensionException {
        if (matrix1.getNcols() != matrix2.getNcols() || matrix1.getNrows() != matrix2.getNrows()) {
            throw new IllegalDimensionException("Two matrices should be the same dimension.");
        }
        Matrix sumMatrix = new Matrix(matrix1.getNrows(), matrix1.getNcols());
        for (int i = 0; i < matrix1.getNrows(); i++) {
            for (int j = 0; j < matrix1.getNcols(); j++) {
                sumMatrix.setValueAt(i, j, matrix1.getValueAt(i, j) + matrix2.getValueAt(i, j));
            }

        }
        return sumMatrix;
    }

    /**
     * subtract two matrices using the above addition method. Matrices should be
     * the same dimension.
     *
     * @param matrix1
     * @param matrix2
     * @return
     * @throws IllegalDimensionException
     */
    public static Matrix subtract(Matrix matrix1, Matrix matrix2) throws IllegalDimensionException {
        return add(matrix1, matrix2.scalarMultiplication(-1));
    }

    /**
     * Multiply two matrices.
     *
     * @param matrix1
     * @param matrix2
     * @return
     */
    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        Matrix multipliedMatrix = new Matrix(matrix1.getNrows(), matrix2.getNcols());

        for (int i = 0; i < multipliedMatrix.getNrows(); i++) {
            for (int j = 0; j < multipliedMatrix.getNcols(); j++) {
                double sum = 0.0;
                for (int k = 0; k < matrix1.getNcols(); k++) {
                    sum += matrix1.getValueAt(i, k) * matrix2.getValueAt(k, j);
                }
                multipliedMatrix.setValueAt(i, j, sum);
            }
        }
        return multipliedMatrix;
    }

}
