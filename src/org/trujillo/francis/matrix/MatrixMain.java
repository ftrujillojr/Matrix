package org.trujillo.francis.matrix;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixMain {

    public static void main(String[] args) {
        try {
            double[][] dArray = new double[][]{
                {1, -3, 0},
                {-2, 4, 1},
                {5, -2, 2},};
            Matrix matrix = new Matrix(dArray);
            

            Matrix tmatrix = MatrixMath.transpose(matrix);
            System.out.println("Transpose\n" + tmatrix.toString());

            Matrix idMat = MatrixMath.getIndentityMatrix(3);
            System.out.println("idMat \n" + idMat.toString());

            System.out.println("====================================");
            System.out.println("Original \n" + matrix.toString());

            double detOriginal = MatrixMath.determinant(matrix);

            System.out.println("DET original => " + detOriginal + "\n");


        } catch (NoSquareException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalDimensionException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
