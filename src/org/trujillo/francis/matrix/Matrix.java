package org.trujillo.francis.matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

// http://onsolver.com/matrix.php
public final class Matrix {

    private static boolean scientificOut = false;
    private static boolean showWork = false;
    private static boolean showSomeWork = true;
    private static int level = 0;

    private int nrows;
    private int ncols;
    private double[][] data;

    public Matrix(double[][] dat) {
        this.data = dat;
        this.nrows = dat.length;
        this.ncols = dat[0].length;
    }

    public Matrix(int nrow, int ncol) {
        this.nrows = nrow;
        this.ncols = ncol;
        // 2 dim array will default to 0.0 at every address.
        data = new double[nrow][ncol];
    }

    // http://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0CB4QFjAA&url=http%3A%2F%2Fstackoverflow.com%2Fquestions%2F22900872%2Fraising-a-matrix-to-the-power-method-java&ei=WdB4VPvLDs3SoASOs4C4Ag&usg=AFQjCNERIkyfAVz1U-wCIrYyRL5FxgsWYQ&sig2=q6s4-3u-6Uf0-h2Bb9Rw3A
    // raise b to the n, n >= 0
    public static Matrix powerOfSquareMatrix(Matrix b, int n) throws NoSquareException {
        if (!b.isSquare()) {
            throw new NoSquareException("ERROR: Matrix need to be square. \n" + b.toString());
        }
        if (n == 0) {
            return Matrix.getIndentityMatrix(b.size()); // b to the 0 is 1
        }
        Matrix c = Matrix.powerOfSquareMatrix(b, n / 2);
        c = Matrix.multiply(c, c);
        if (n % 2 == 0) {
            return c; // case when n is even
        }
        return multiply(c, b); // case when n is odd
    }
    
    /**
     * Transpose of a matrix - Swap the columns with rows
     * http://en.wikipedia.org/wiki/Transpose
     *
     * @param matrix
     * @return Matrix
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
     * <pre>
     * Inverse of a matrix
     *    A-1 * A = I where I is the identity matrix.
     *
     * A matrix that have inverse is called non-singular or invertible.
     *
     * If the matrix does not have inverse it is called singular.
     *
     * For a singular matrix the values of the inverted matrix are either NAN
     * or Infinity
     *
     * Only square matrices have inverse and the following method will throw exception if
     * the matrix is not square or if there is NOT a solution or multiple solutions.
     *
     * </pre>
     *
     * @param matrix
     * @return
     * @throws NoSquareException
     * @throws org.trujillo.francis.matrix.IllegalDimensionException
     * @throws org.trujillo.francis.matrix.NoSolutionOrMultipleSolutions
     */
    public static Matrix inverse(Matrix matrix) throws NoSquareException, IllegalDimensionException, NoSolutionOrMultipleSolutions {
        if (showWork) {
            System.out.println("\n*** Calculating inverse() of Matrix A\n" + matrix.toString());
        }
        double det = determinant(matrix);
        if (det == 0) {
            String msg = "Matrix A => returned ZERO for determinant.  Has NO solution or multiple solutions.\n";
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
     * @return double
     * @throws NoSquareException
     * @throws org.trujillo.francis.matrix.IllegalDimensionException
     */
    public static double determinant(Matrix matrix) throws NoSquareException, IllegalDimensionException {
        String indent = matrix.fill("        ", level++, "");
        String lookAheadIndent = matrix.fill("        ", level, "");

        if (!matrix.isSquare()) {
            throw new NoSquareException("ERROR: Matrix need to be square. \n" + matrix.toString());
        }

        String callingMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

        // Do not display on recursive call.  Only on top level call.
        if (callingMethodName.equals("determinant") == false) {
            if (showWork) {
                if (callingMethodName.equals("inverse")) {
                    System.out.println(indent + "Calculating determinant for " + callingMethodName.toUpperCase() + " Matrix A by expansion of minors.");
                } else {
                    System.out.println(indent + "Calculating determinant for " + callingMethodName.toUpperCase() + " Matrix A");
                }
                System.out.println(indent + "Walking across top row, removing row/col. . .resulting in new Matrix.\n");
                System.out.println(matrix.toString(8 * level));
            }
        }

        if (matrix.size() == 1) {
            level--;
            return matrix.getValueAt(0, 0);
        }

        if (matrix.size() == 2) {
            // | a  b |
            // | c  d |
            //
            // (ad) - (bc)
            double det = (matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - (matrix.getValueAt(0, 1) * matrix.getValueAt(1, 0));
            det = Matrix.fixNegativeZero(det);
            level--;
            return (det);
        }

        double sum = 0.0;
        for (int i = 0; i < matrix.getNcols(); i++) {
            Matrix subMat = createSubMatrix(matrix, 0, i);
            if (showWork) {
                System.out.println(lookAheadIndent + "Value @ MATRIX [" + 0 + "][" + i + "] => " + matrix.getValueAt(0, i) + "\n");
                System.out.println(subMat.toString(8 * level));
            }
            double det = determinant(subMat);  // RECURSIVE part..
            double total = Matrix.fixNegativeZero(matrix.getValueAt(0, i) * det);

            if (showWork) {
                System.out.println(lookAheadIndent + "DET " + det + " * " + matrix.getValueAt(0, i) + " => " + total + "  then, AFTER Change sign " + (changeSign(i) * total) + " \n");
            }
            sum += changeSign(i) * total;
        }
        sum = Matrix.fixNegativeZero(sum);
        if (showWork) {
            System.out.println(indent + "SUM of the cofactor DET (above) => " + sum + "\n");
        }
        level--;
        return sum;
    }

    /**
     * Determine the sign; i.e. even numbers have sign + and odds -
     *
     * @param i
     * @return 1 or -1
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
     * @return Matrix
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
     * @return Matrix
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
//                if (showWork) {
//                    String changeSignI = (i % 2 == 0) ? "NO" : "YES";
//                    String changeSignJ = (j % 2 == 0) ? "NO" : "YES";
//                    System.out.println("DET of cofactor " + det + "  DET after sign change " + val + "    i,j: " + i + " " + j + " " + "ChangeSign: " + changeSignI + " " + changeSignJ + "\n");
//                }
                total += val;
                mat.setValueAt(i, j, val);
            }
        }
//        if (showWork) {
//            System.out.println("COFACTOR with DET total => " + total + "\n" + mat.toString());
//        }
        return mat;
    }

    /**
     * Adds two matrices of the same dimension
     *
     * @param matrix1
     * @param matrix2
     * @return Matrix
     * @throws IllegalDimensionException
     */
    public static Matrix add(Matrix matrix1, Matrix matrix2) throws IllegalDimensionException {
        if (matrix1.getNcols() != matrix2.getNcols() || matrix1.getNrows() != matrix2.getNrows()) {
            String msg = "\nERROR: Two matrices should be the same dimension.\n";
            msg += matrix1.toString();
            msg += matrix1.fill("=", 40, "");
            msg += "\n";
            msg += matrix2.toString();
            throw new IllegalDimensionException(msg);
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
     * @return Matrix
     * @throws IllegalDimensionException
     */
    public static Matrix subtract(Matrix matrix1, Matrix matrix2) throws IllegalDimensionException {
        return add(matrix1, matrix2.scalarMultiplication(-1));
    }

    /**
     * Multiply two matrices. A*B != B*A Not communitive
     * http://en.wikipedia.org/wiki/Matrix_multiplication
     *
     * @param matrix1
     * @param matrix2
     * @return Matrix
     */
    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        Matrix multipliedMatrix = new Matrix(matrix1.getNrows(), matrix2.getNcols());

        for (int i = 0; i < multipliedMatrix.getNrows(); i++) {
            for (int j = 0; j < multipliedMatrix.getNcols(); j++) {
                double sum = 0.0;
                for (int k = 0; k < matrix1.getNcols(); k++) {
                    sum += matrix1.getValueAt(i, k) * matrix2.getValueAt(k, j);
                }

                sum = Matrix.fixNegativeZero(sum);

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

     /**
     * If you take 0.00 * -12.0 then you get -0.00. This method fixes this.
     *
     * http://en.wikipedia.org/wiki/Signed_zero
     *
     * @param val
     * @return
     */
    private static double fixNegativeZero(double val) {
        double returnValue = val;
        // -0.0 problem.
        if (returnValue > -0.1 && returnValue < 0.0000000000000001) {
            returnValue = 0.00;
        }
        return (returnValue);
    }

    public static void setScienticOut() {
        scientificOut = true;
    }

    public static void clrScienticOut() {
        scientificOut = false;
    }

    public static void setShowWork() {
        showWork = true;
    }

    public static void setShowSomeWork() {
        showSomeWork = true;
    }

    public static boolean getShowWork() {
        return showWork;
    }

    public static void clrShowWork() {
        showWork = false;
    }

    public static void clrShowSomeWork() {
        showSomeWork = false;
    }

    public static Matrix solveSystemOfLinearEquations(Matrix matrix, Matrix vector) throws NoSquareException, NoSolutionOrMultipleSolutions, IllegalDimensionException, InverseMatrixIncorrectException {
        boolean savedShowWork = showWork;

        if (showSomeWork | showWork) {
            String banner = matrix.fill("=", 100, "");
            System.out.println(banner + "\n");
            System.out.println("Vector B\n" + vector.toString());
            System.out.println("Matrix A\n\n" + matrix.toString());
        }

        if (!matrix.isSquare()) {
            String msg = "Solving System of Linear Equations requires a SQUARE matrix.";
            throw new NoSquareException(msg);
        }

        clrShowWork();
        Matrix identityMatrix = Matrix.getIndentityMatrix(matrix.ncols);

        double detA = Matrix.determinant(matrix);
        if (savedShowWork) {
            setShowWork();
        }
        if (showSomeWork | showWork) {
            System.out.println("Determinant of A => " + detA + "\n");
            System.out.println("Division is not defined for a Matrix.");
            System.out.println("So, we must use Inverse of Matrix A and MULTIPLY by Vector B.  A^-1 * B\n");
        }
        Matrix invMatrix = Matrix.inverse(matrix);

        clrShowWork();
        // This is just to verify that we calculated the invMatrix correctly.  Being paranoid.
        Matrix shouldBeIdentityMatrix = Matrix.multiply(matrix, invMatrix);
        if (shouldBeIdentityMatrix.isEqualTo(identityMatrix, 5) == false) {
            String msg = "ERROR: Multiplying matrix and invMatrix should equal identity matrix.  PROGRAMMER ERROR\n";
            msg += identityMatrix.toString();
            msg += "\n";
            msg += shouldBeIdentityMatrix.toString();
            throw new InverseMatrixIncorrectException(msg);
        }
        if (savedShowWork) {
            setShowWork();
        }

        if (savedShowWork) {
            System.out.println("So, we must use Inverse of Matrix A and MULTIPLY by Vector B.  A^-1 * B\n");
        }

        if (showSomeWork) {
            setShowWork();
        }
        Matrix resultSysOfEq = Matrix.multiply(invMatrix, vector);
        if (savedShowWork == false) {
            clrShowWork();
        }
        return (resultSysOfEq);
    }

    public static double calculateAreaOfTriangleUsing3CoordinateMatrix(Matrix coordinateMatrix) throws NoSquareException, IllegalDimensionException {
        Matrix areaTriangleMatrix = coordinateMatrix.appendColumnWithValue1();

        if (showSomeWork | showWork) {
            String banner = coordinateMatrix.fill("=", 100, "");
            System.out.println(banner + "\n");
            System.out.println("Coordinates of triangle\n" + coordinateMatrix.toString());
        }

        if (showWork) {
            System.out.println("Create new matrix adding a column with ones \n" + areaTriangleMatrix.toString(8));
        }

        double det = Matrix.determinant(areaTriangleMatrix);
        if (showWork) {
            System.out.println("Find DET => " + det);
        }

        double area = Math.abs(0.5 * det);
        if (showSomeWork | showWork) {
            System.out.println("AREA of triangle is   +/-  1/2 * det => " + area + "  units^2    (always positive units)\n");
        }

        return (area);
    }

// ======================================================================================================
    /**
     * This is set to default scale of 6. You may call isEqualTo(matrix, scale)
     * to control scale.
     *
     * @param matB
     * @return boolean
     */
    public boolean isEqualTo(Matrix matB) {
        return (this.isEqualTo(matB, 6));
    }

    /**
     * This method will be used mainly in Tests for expect results.
     *
     * @param matB
     * @param scale
     * @return boolean
     */
    public boolean isEqualTo(Matrix matB, int scale) {
        boolean bothEqual = true;

        if ((this.nrows != matB.nrows) || (this.ncols != matB.ncols)) {
            bothEqual = false;
        } else {
            for (int ii = 0; ii < this.nrows; ii++) {
                for (int jj = 0; jj < this.ncols; jj++) {
                    Double a = this.roundHalfUp(this.getValueAt(ii, jj), scale);
                    Double b = this.roundHalfUp(matB.getValueAt(ii, jj), scale);
                    if (a.equals(b) == false) {
                        System.out.println(String.format("ERROR:  a => %20.12f    b => %20.12f\n", a, b));
                        bothEqual = false;
                        break;
                    }
                }
                if (!bothEqual) {
                    break;
                }
            }
        }
        return (bothEqual);
    }

    /**
     * Floating point arithmetic has rounding errors. This method sets a
     * precision past the decimal and rounds up past .0000000000005 if scale was
     * 12.
     *
     * My intent is to only use this on isEqualTo() method for comparison.
     *
     * @param val
     * @param scale
     * @return
     */
    public double roundHalfUp(double val, int scale) {
        BigDecimal bdA = BigDecimal.valueOf(val).setScale(scale, RoundingMode.HALF_UP);
        return (bdA.doubleValue());
    }

    public int getNrows() {
        return nrows;
    }

    public int getNcols() {
        return ncols;
    }

    public double[][] getValues() {
        return data;
    }

    public void setValueAt(int row, int col, double value) {
        data[row][col] = value;
    }

    public double getValueAt(int row, int col) {
        return data[row][col];
    }

    public boolean isSquare() {
        return nrows == ncols;
    }

    public int size() {
        if (isSquare()) {
            return nrows;
        }
        return -1;
    }

    /**
     * <pre>
     * The rank is defined as the largest square matrix that could fit inside
     * this matrix.
     * </pre>
     *
     * @return
     */
    public int rank() {
        if (nrows >= ncols) {
            return ncols;
        } else {
            return nrows;
        }
    }

    /**
     * <pre>
     * This is used for toString(int width) to position matrix.
     * </pre>
     *
     * @param fillStr
     * @param num
     * @param myString
     * @return String
     */
    private String fill(String fillStr, int num, String myString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num - myString.length(); i++) {
            sb.append(fillStr);
        }
        sb.append(myString);
        return (sb.toString());
    }

    /**
     * <pre>
     * This allows the Matrix to be displayed similar to the way we would
     * write it.
     * </pre>
     *
     * @return
     */
    @Override
    public String toString() {
        return (this.toString(0));
    }

    /**
     * Allows Matrix to be displayed "width" shifted to the right.
     *
     * @param width
     * @return
     */
    public String toString(int width) {
        String spacer = this.fill(" ", width, "");
        NumberFormat formatter = new DecimalFormat("0.######E0");

        StringBuilder strBuilder = new StringBuilder();
        for (int ii = 0; ii < this.getNrows(); ii++) {
            strBuilder.append(spacer);
            strBuilder.append("| ");
            for (int jj = 0; jj < this.getNcols(); jj++) {
                if (scientificOut) {
                    strBuilder.append(String.format("%15s ", formatter.format(this.getValueAt(ii, jj))));
                } else {
                    strBuilder.append(String.format("%12.5f ", this.getValueAt(ii, jj)));
                }
            }
            strBuilder.append("| \n");
        }
        return strBuilder.toString();
    }

    /**
     * Take the current matrix this.data and multiply by a constant.
     *
     * This is the only non static math method and is chained.
     *
     * @param constant
     * @return Matrix
     */
    public Matrix scalarMultiplication(double constant) {
        Matrix mat = new Matrix(nrows, ncols);

        if (showWork) {
            System.out.println("BEFORE Scalar Mult by  => " + constant);
            System.out.println(this.toString());
        }

        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                mat.setValueAt(i, j, Matrix.fixNegativeZero(this.data[i][j]) * constant);
            }
        }
        if (showWork) {
            System.out.println("AFTER Scalar Mult");
            System.out.println(mat.toString());
        }
        return mat;
    }

    public Matrix insertColumnWithValue1() {
        Matrix mat = new Matrix(this.getNrows(), this.getNcols() + 1);
        for (int i = 0; i < mat.getNrows(); i++) {
            for (int j = 0; j < mat.getNcols(); j++) {
                if (j == 0) {
                    mat.setValueAt(i, j, 1.0);
                } else {
                    mat.setValueAt(i, j, this.getValueAt(i, j - 1));
                }

            }
        }
        return mat;
    }

    public Matrix appendColumnWithValue1() {
        Matrix mat = new Matrix(this.getNrows(), this.getNcols() + 1);
        for (int i = 0; i < mat.getNrows(); i++) {
            for (int j = 0; j < mat.getNcols(); j++) {
                if (j == (mat.getNcols() - 1)) {
                    mat.setValueAt(i, j, 1.0);
                } else {
                    mat.setValueAt(i, j, this.getValueAt(i, j));
                }

            }
        }
        return mat;
    }

}
