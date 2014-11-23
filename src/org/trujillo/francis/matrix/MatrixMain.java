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

            Matrix resultSysEq = Matrix.solveSystemOfLinearEquations(matrix, vector, false);
            
            
            
        } catch (NoSquareException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalDimensionException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSolutionOrMultipleSolutions ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
