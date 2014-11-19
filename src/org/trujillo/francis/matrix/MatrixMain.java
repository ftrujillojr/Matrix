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
            System.out.println("Original Matrix \n" + matrix.toString());

            Matrix idMat = MatrixMath.getIndentityMatrix(3);
            System.out.println("INDENTITY of Matrix \n" + idMat.toString());

            System.out.println("====================================");
            double detOriginal = MatrixMath.determinant(matrix);
            System.out.println("DETERMINANT of original => " + detOriginal);
            System.out.println("====================================");
            
            Matrix invMatrix = MatrixMath.inverse(matrix);
            System.out.println("INV of Matrix \n" + invMatrix );
            System.out.println("====================================");
            
            Matrix resultMatrix = MatrixMath.multiply(matrix, invMatrix);
            System.out.println("SHOULD be EQUAL to IDENTITY matrix \n" + resultMatrix);


        } catch (NoSquareException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalDimensionException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSolutionOrMultipleSolutions ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
