package org.trujillo.francis.matrix;

public class Matrix {

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

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        for (int ii = 0; ii < this.getNrows(); ii++) {
            strBuilder.append("| ");
            for (int jj = 0; jj < this.getNcols(); jj++) {
                strBuilder.append(String.format("%9.2f ", this.getValueAt(ii, jj)));
            }
            strBuilder.append("| \n");
        }
        return strBuilder.toString();
    }

    public int getNrows() {
        return nrows;
    }

//    public void setNrows(int nrows) {
//        this.nrows = nrows;
//    }

    public int getNcols() {
        return ncols;
    }

//    public void setNcols(int ncols) {
//        this.ncols = ncols;
//    }

    public double[][] getValues() {
        return data;
    }

//    public void setValues(double[][] values) {
//        this.data = values;
//    }

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
