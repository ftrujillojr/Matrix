package org.trujillo.francis.matrix;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Matrix {

    private static boolean scientificOut = false;

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

    /**
     * <pre>
     * This is used for toString(int width) to position matrix.
     * </pre>
     *
     * @param fillStr
     * @param num
     * @param myString
     * @return
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
     * This value 1.999999999999999999 is truncated to 2.0 and this value
     * 4.00000000000023456 is truncated to 4.0
     *
     * The number of ZEROs gives us the precision. There seems to be a limit at
     * 9 digits with this method. This is good enough for now. TODO: find method
     * to 18 decimal places
     *
     * @param x
     * @return
     */
    private double truncate(double x) {
        if (x > 0) {
            return Math.floor(x * 1000000000) / 1000000000;
        } else {
            return Math.ceil(x * 1000000000) / 1000000000;
        }
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        NumberFormat formatter = new DecimalFormat("0.######E0");

        for (int ii = 0; ii < this.getNrows(); ii++) {
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

    public Matrix scalarMultiplication(double constant) {
        Matrix mat = new Matrix(nrows, ncols);
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                mat.setValueAt(i, j, data[i][j] * constant);
            }
        }
        return mat;
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

    public static void setScienticOut() {
        scientificOut = true;
    }

    public static void clrScienticOut() {
        scientificOut = false;
    }

}
