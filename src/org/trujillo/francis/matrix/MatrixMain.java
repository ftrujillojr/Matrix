package org.trujillo.francis.matrix;

import java.util.logging.Level;
import java.util.logging.Logger;

// http://www.mathsisfun.com/algebra/matrix-calculator.html
public class MatrixMain {

    public static void main(String[] args) {
        try {
            double[][] mat = new double[][]{
                {3, 2, -1, 5},
                {2, -2, 4, 2},
                {-1, 1.0 / 2.0, -1, 0},
                {1, 2, 3, 4},};

            double[][] vec = new double[][]{
                {1},
                {-2},
                {0},
                {-2}
            };

            Matrix matrix = new Matrix(mat);
            Matrix vector = new Matrix(vec);

            //Matrix.setShowWork();
            //Matrix.clrShowSomeWork();
            Matrix resultSysEq = Matrix.solveSystemOfLinearEquations(matrix, vector);
            

            
            
        } catch (NoSquareException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalDimensionException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSolutionOrMultipleSolutions ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InverseMatrixIncorrectException ex) {
            Logger.getLogger(MatrixMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
