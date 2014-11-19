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

    public static void setScienticOut() {
        scientificOut = true;
    }

    public static void clrScienticOut() {
        scientificOut = false;
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
                    strBuilder.append(String.format("%10.5f ", this.getValueAt(ii, jj)));
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
                    strBuilder.append(String.format("%10.5f ", this.getValueAt(ii, jj)));
                }
            }
            strBuilder.append("| \n");
        }
        return strBuilder.toString();
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

    public Matrix scalarMultiplication(double constant) {
        Matrix mat = new Matrix(nrows, ncols);
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                mat.setValueAt(i, j, data[i][j] * constant);
            }
        }
        return mat;
    }

    private String fill(String fillStr, int num, String myString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num - myString.length(); i++) {
            sb.append(fillStr);
        }
        sb.append(myString);
        return (sb.toString());
    }

//    public Matrix insertColumnWithValue1() {
//        Matrix mat = new Matrix(this.getNrows(), this.getNcols() + 1);
//        for (int i = 0; i < mat.getNrows(); i++) {
//            for (int j = 0; j < mat.getNcols(); j++) {
//                if (j == 0) {
//                    mat.setValueAt(i, j, 1.0);
//                } else {
//                    mat.setValueAt(i, j, this.getValueAt(i, j - 1));
//                }
//
//            }
//        }
//        return mat;
//    }
}
