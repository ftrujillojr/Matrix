package org.trujillo.francis.matrix;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixMain {

    public static void main(String[] args) {
//        try {
            double[][] dArray = new double[][] {
                {1,2},
                {0,3},
            };
            
            Matrix matrix = new Matrix(dArray);
            System.out.println("Original \n" + matrix.toString());
            
            Matrix tmatrix = MatrixMath.transpose(matrix);
            System.out.println("Transpose\n" + tmatrix.toString());
            
            Matrix idMat = MatrixMath.getIndentityMatrix(4);
            System.out.println("idMat \n" + idMat.toString());
            
            
//        } 
//        catch (IllegalDimensionException ex) {
//            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
