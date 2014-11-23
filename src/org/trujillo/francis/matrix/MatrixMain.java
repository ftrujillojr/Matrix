package org.trujillo.francis.matrix;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixMain {

    public static void main(String[] args) {
        try {
            double[][] mat = new double[][]{
                {3, 2, -1},
                {2, -2, 4},
                {-1, 1.0 / 2.0, -1}
            };

            double[][] vec = new double[][]{
                {1},
                {-2},
                {0}
            };

            Matrix matrix = new Matrix(mat);
            Matrix vector = new Matrix(vec);

            System.out.println("Matrix \n" + matrix.toString());

            System.out.println("Vector \n" + vector.toString());

            Matrix invMatrix = Matrix.inverse(matrix);
            System.out.println("Inverse Matrix \n" + invMatrix.toString());

            boolean verifyInvMat = false;
            if (verifyInvMat) {
                Matrix.setShowWork();
                System.out.println("(Verify INV Matrix is correct) Multiplying Original Matrix with the Inverse Matrix == IDENTITY Matrix\n");
                Matrix resultMatrix = Matrix.multiply(matrix, invMatrix);
                Matrix.clrShowWork();
            }

            Matrix.setShowWork();
            System.out.println("Multiply  Inverse Matrix by Vector to solve linear system of equations.");
            Matrix resultSysOfEq = Matrix.multiply(invMatrix, vector);
            Matrix.clrShowWork();
            System.out.println(" \n" + resultSysOfEq.toString());
            System.out.println("===============================================================\n");

        } catch (NoSquareException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalDimensionException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSolutionOrMultipleSolutions ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
