package org.trujillo.francis.matrix;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixMain {

    public static void main(String[] args) {
        try {
            double[][] dArray = new double[][]{
                {3, 2, -1},
                {2, -2, 4},
                {-1, 1.0 / 2.0, -1},};

            double[][] dArray2 = new double[][]{
                {1},
                {-2},
                {0},};
            
            //Matrix.setShowWork();
            //Matrix.setScienticOut();

            Matrix matrix = new Matrix(dArray);
            Matrix vector = new Matrix(dArray2);
            
            System.out.println("Matrix \n" + matrix.toString());
            System.out.println("====================================");
            
            System.out.println("Vector \n" + vector.toString());
            System.out.println("====================================");
            
            Matrix invMatrix = Matrix.inverse(matrix);
            System.out.println("Inverse Matrix \n" + invMatrix.toString());
            System.out.println("====================================\n");

            Matrix.setShowWork();
            System.out.println("(Verify INV Matrix is correct) Multiplying Original Matrix with the Inverse Matrix == IDENTITY Matrix\n");
            Matrix resultMatrix = Matrix.multiply(matrix, invMatrix);
            System.out.println("RESULT from multiply \n" + resultMatrix.toString());
            System.out.println("====================================\n");

            Matrix sysOfEq = Matrix.multiply(invMatrix, vector);
            Matrix.clrShowWork();
            System.out.println("Solved System of equations \n" + sysOfEq.toString());
            System.out.println("====================================\n");
            
        } catch (NoSquareException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalDimensionException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSolutionOrMultipleSolutions ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
