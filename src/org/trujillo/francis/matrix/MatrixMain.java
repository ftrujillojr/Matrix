package org.trujillo.francis.matrix;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixMain {

    public static void main(String[] args) {
        try {
            double[][] dArray = new double[][] {
                {1,2},
                {0,3},
            };
            
            Matrix matrix = new Matrix(dArray);
            System.out.println("Original \n" + matrix.toString());
            
            Matrix tmatrix = MatrixMath.transpose(matrix);
            System.out.println("Transpose\n" + tmatrix.toString());
            
            
            Matrix subMatrix = MatrixMath.createSubMatrix(matrix, 1, 1);
            System.out.println("Sub Matrix 0,0 removed\n" + subMatrix.toString());
        } catch (IllegalDimensionException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }

}
