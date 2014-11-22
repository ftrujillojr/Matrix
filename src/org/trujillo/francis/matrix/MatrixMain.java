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
            
            
            MatrixMath.setShowWork();
            //Matrix.setScienticOut();
            
            Matrix matrix = new Matrix(dArray);
            System.out.println("Matrix \n" + matrix.toString());
            
            System.out.println("====================================");
            double detOriginal = MatrixMath.determinant(matrix);
            System.out.println("DETERMINANT of Original Matrix => " + detOriginal);
            System.out.println("====================================\n");
            //System.exit(99);
            
            Matrix invMatrix = MatrixMath.inverse(matrix);
            System.out.println("Inverse Matrix \n" + invMatrix.toString());
            System.out.println("====================================\n");
            

            System.out.println("Multiplying Original Matrix with the Inverse Matrix == IDENTITY Matrix\n");
            Matrix resultMatrix = MatrixMath.multiply(matrix, invMatrix);
            System.out.println("RESULT from multiply \n" + resultMatrix.toString());
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
