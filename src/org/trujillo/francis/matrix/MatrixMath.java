package org.trujillo.francis.matrix;

/**
 * http://matrix.reshish.com/
 *
 */
public class MatrixMath {

    private static boolean showWork = false;

    /**
     * This class a matrix utility class and cannot be instantiated.
     */
    private MatrixMath() {
    }

    public static void setShowWork() {
        showWork = true;
    }

    public static void clrShowWork() {
        showWork = false;
    }

    /**
     * Transpose of a matrix - Swap the columns with rows
     *
     * @param matrix
     * @return okay
     */
    public static Matrix transpose(Matrix matrix) {
        Matrix transposedMatrix = new Matrix(matrix.getNcols(), matrix.getNrows());
        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols(); j++) {
                transposedMatrix.setValueAt(j, i, matrix.getValueAt(i, j));
            }
        }
        if (showWork) {
            System.out.println("TRANSPOSE \n" + transposedMatrix);
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
     * @throws org.trujillo.francis.matrix.NoSolutionOrMultipleSolutions
     */
    public static Matrix inverse(Matrix matrix) throws NoSquareException, IllegalDimensionException, NoSolutionOrMultipleSolutions {
        if (showWork) {
            System.out.println("\n*** Calculating inverse() of\n" + matrix.toString());
        }
        double det = determinant(matrix);
        if (det == 0) {
            String msg = "Matrix => return ZERO for determinant.  Has NO solution or multiple solutions.\n";
            msg += matrix.toString();
            throw new NoSolutionOrMultipleSolutions(msg);
        }
        if (showWork) {
            System.out.println("SCALAR MULT by " + (1.0 / det) + "      DET was " + det + "\n");
        }
        return (transpose(cofactor(matrix)).scalarMultiplication(1.0 / det));
    }

    /**
     * Get the identity matrix for a SQUARE matrix of size.
     *
     * @param size
     * @return okay
     */
    public static Matrix getIndentityMatrix(int size) {
        Matrix idMat = new Matrix(size, size);

        for (int ii = 0; ii < size; ii++) {
            for (int jj = 0; jj < size; jj++) {
                if (ii == jj) {
                    idMat.setValueAt(ii, jj, 1.0);
                }
            }
        }
        return (idMat);
    }

    /**
     * Determinant of a square matrix The following function find the
     * determinant in a recursively.
     *
     * In linear algebra, the determinant is a value associated with a square
     * matrix.
     *
     * In the first case the system has a unique solution exactly when the
     * determinant is nonzero; when the determinant is zero there are either no
     * solutions or many solutions
     *
     * In the second case the transformation has an inverse operation exactly
     * when the determinant is nonzero.
     *
     * The determinant of the identity matrix is 1.
     *
     * Using the first row to walk across to do cofactor expansion.
     * http://mathworld.wolfram.com/DeterminantExpansionbyMinors.html
     *
     *
     * @param matrix
     * @return okay
     * @throws NoSquareException
     * @throws org.trujillo.francis.matrix.IllegalDimensionException
     */
    public static double determinant(Matrix matrix) throws NoSquareException, IllegalDimensionException {
        if (!matrix.isSquare()) {
            throw new NoSquareException("ERROR: Matrix need to be square. \n" + matrix.toString());
        }
        String callingMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        if (callingMethodName.equals("determinant") == false) {
            if (showWork) {
                System.out.println("*** calling determinant  => from " + callingMethodName + "\n" + matrix.toString());
            }
        }

        if (matrix.size() == 1) {
            return matrix.getValueAt(0, 0);
        }

        if (matrix.size() == 2) {
            // | a  b |
            // | c  d |
            //
            // (ad) - (bc)
            if (showWork) {
                //System.out.println("(ad) - (bc)\n");
            }
            double det = (matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - (matrix.getValueAt(0, 1) * matrix.getValueAt(1, 0));
            return (det);
        }

        double sum = 0.0;
        for (int i = 0; i < matrix.getNcols(); i++) {
            Matrix subMat = createSubMatrix(matrix, 0, i);
            double det = determinant(subMat);
            double total = matrix.getValueAt(0, i) * det;

            if (showWork) {
                System.out.println("SUB MAT " + 0 + " " + i + " => " + matrix.getValueAt(0, i) + " mult by DET below  \n" + subMat + "DET " + det + " = " + total + "  AFTER Change sign " + (changeSign(i) * total) + " \n");
            }
            sum += changeSign(i) * total;
        }
        return sum;
    }

    /**
     * Determine the sign; i.e. even numbers have sign + and odds -
     *
     * @param i
     * @return okay
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
     * @throws org.trujillo.francis.matrix.IllegalDimensionException
     */
    public static Matrix createSubMatrix(Matrix matrix, int excluding_row, int excluding_col) throws IllegalDimensionException {
        if (excluding_row > matrix.getNrows() - 1) {
            String msg = "You specified to exclude a row # " + excluding_row + " and index was out of bounds.";
            throw new IllegalDimensionException(msg);
        }
        if (excluding_col > matrix.getNcols() - 1) {
            String msg = "You specified to exclude a col # " + excluding_col + " and index was out of bounds.";
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
        double total = 0.0;

        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols(); j++) {
                double det = determinant(createSubMatrix(matrix, i, j));
                double val = changeSign(i) * changeSign(j) * det;
                if (showWork) {
                    String changeSignI = (i % 2 == 0) ? "NO" : "YES";
                    String changeSignJ = (j % 2 == 0) ? "NO" : "YES";
                    System.out.println("DET (cofactor) " + det + "  Value " + val + "    i,j: " + i + " " + j + " " + "ChangeSign: " + changeSignI + " " + changeSignJ + "\n");
                }
                total += val;
                mat.setValueAt(i, j, val);
            }
        }
        if (showWork) {
            System.out.println("COFACTOR with DET total => " + total + "\n" + mat.toString());
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

                if (sum > 0.99999999999999 && sum < 1.0) {
                    sum = 1.0;
                }

                sum = MatrixMath.truncate(sum);
                // -0.0 problem.
                if (sum > -0.1 && sum < 0.0000000000000001) {
                    sum = 0.00;
                }

                //System.err.println("i " + i + " j " + j + " " + sum);
                multipliedMatrix.setValueAt(i, j, sum);
            }
        }
        
        if (showWork) {
            String mat1Str = matrix1.toString();
            String[] mat1Array = mat1Str.split("\n");
            int widthMat1 = mat1Array[0].length();
            System.out.println(matrix2.toString(widthMat1));
            String multMat = multipliedMatrix.toString();
            String[] multMatArray = multMat.split("\n");
            for (int jj = 0; jj < mat1Array.length; jj++) {
                String tmp = mat1Array[jj] + multMatArray[jj];
                System.out.println(tmp);
            }
            System.out.println("\n");
        }

        return multipliedMatrix;
    }

    private static double truncate(double x) {
        if (x > 0) {
            return Math.floor(x * 1000000000) / 1000000000;
        } else {
            return Math.ceil(x * 1000000000) / 1000000000;
        }
    }

}
