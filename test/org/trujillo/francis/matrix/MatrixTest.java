package org.trujillo.francis.matrix;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MatrixTest {

    public MatrixTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of transpose method, of class Matrix.
     */
    @Test
    public void testTranspose() {
        double[][] mat = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Matrix matrix = new Matrix(mat);

        double[][] exp = {
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9}
        };
        Matrix expResult = new Matrix(exp);
        Matrix result = Matrix.transpose(matrix);
        this.displayResultExpectIfNotEqual(expResult, result, 12);
        assertTrue(result.isEqualTo(expResult, 12));
    }

    @Test
    public void testTranspose_2() {
        double[][] mat = {
            {1, 2, 3},
            {4, 5, 6}
        };
        Matrix matrix = new Matrix(mat);

        double[][] exp = {
            {1, 4},
            {2, 5},
            {3, 6}
        };
        Matrix expResult = new Matrix(exp);
        Matrix result = Matrix.transpose(matrix);
        this.displayResultExpectIfNotEqual(expResult, result, 12);
        assertTrue(result.isEqualTo(expResult, 12));
    }

    /**
     * Test of inverse method, of class Matrix.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testInverse() throws Exception {
        double[][] mat = {
            {0, -1.2, 3.0},
            {5.12, 5.0, 0},
            {1, 4, -1}
        };
        Matrix matrix = new Matrix(mat);

        double[][] exp = {
            {-0.12408, 0.26802, -0.37225},
            {0.12706, -0.07445, 0.38118},
            {0.38416, -0.02978, 0.15247}
        };
        Matrix expResult = new Matrix(exp);
        Matrix result = Matrix.inverse(matrix);
        this.displayResultExpectIfNotEqual(expResult, result, 5);
        assertTrue(result.isEqualTo(expResult, 5));
    }

    /**
     * Test of getIndentityMatrix method, of class Matrix.
     */
    @Test
    public void testGetIndentityMatrix() {
        double[][] exp3 = {
            {1.0, 0.0, 0.0},
            {0.0, 1.0, 0.0},
            {0.0, 0.0, 1.0},};
        Matrix expResult3 = new Matrix(exp3);

        Matrix result3 = Matrix.getIndentityMatrix(3);
        this.displayResultExpectIfNotEqual(expResult3, result3, 5);
        assertTrue(result3.isEqualTo(expResult3, 5));

        double[][] exp4 = {
            {1.0, 0.0, 0.0, 0.0},
            {0.0, 1.0, 0.0, 0.0},
            {0.0, 0.0, 1.0, 0.0},
            {0.0, 0.0, 0.0, 1.0},
        };
        Matrix expResult4 = new Matrix(exp4);
        Matrix result4 = Matrix.getIndentityMatrix(4);
        this.displayResultExpectIfNotEqual(expResult4, result4, 5);
        assertTrue(result4.isEqualTo(expResult4, 5));
        
        double[][] exp2 = {
            {1.0, 0.0},
            {0.0, 1.0}
        };
        Matrix expResult2 = new Matrix(exp2);
        Matrix result2 = Matrix.getIndentityMatrix(2);
        this.displayResultExpectIfNotEqual(expResult2, result2, 5);
        assertTrue(result2.isEqualTo(expResult2, 5));
    }

    /**
     * Test of determinant method, of class Matrix.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDeterminant() throws Exception {
        Matrix matrix = null;
        double expResult = 0.0;
        double result = Matrix.determinant(matrix);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createSubMatrix method, of class Matrix.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateSubMatrix() throws Exception {
        Matrix matrix = null;
        int excluding_row = 0;
        int excluding_col = 0;
        Matrix expResult = null;
        Matrix result = Matrix.createSubMatrix(matrix, excluding_row, excluding_col);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cofactor method, of class Matrix.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCofactor() throws Exception {
        Matrix matrix = null;
        Matrix expResult = null;
        Matrix result = Matrix.cofactor(matrix);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Matrix.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAdd() throws Exception {
        Matrix matrix1 = null;
        Matrix matrix2 = null;
        Matrix expResult = null;
        Matrix result = Matrix.add(matrix1, matrix2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subtract method, of class Matrix.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSubtract() throws Exception {
        Matrix matrix1 = null;
        Matrix matrix2 = null;
        Matrix expResult = null;
        Matrix result = Matrix.subtract(matrix1, matrix2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of multiply method, of class Matrix.
     */
    @Test
    public void testMultiply() {
        Matrix matrix1 = null;
        Matrix matrix2 = null;
        Matrix expResult = null;
        Matrix result = Matrix.multiply(matrix1, matrix2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of solveSystemOfLinearEquations method, of class Matrix.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSolveSystemOfLinearEquations() throws Exception {
        Matrix matrix = null;
        Matrix vector = null;
        Matrix expResult = null;
        Matrix result = Matrix.solveSystemOfLinearEquations(matrix, vector);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEqualTo method, of class Matrix.
     */
    @Test
    public void testIsEqualTo_Matrix() {
        Matrix matB = null;
        Matrix instance = null;
        boolean expResult = false;
        boolean result = instance.isEqualTo(matB);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEqualTo method, of class Matrix.
     */
    @Test
    public void testIsEqualTo_Matrix_int() {
        Matrix matB = null;
        int scale = 0;
        Matrix instance = null;
        boolean expResult = false;
        boolean result = instance.isEqualTo(matB, scale);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scalarMultiplication method, of class Matrix.
     */
    @Test
    public void testScalarMultiplication() {
        double constant = 0.0;
        Matrix instance = null;
        Matrix expResult = null;
        Matrix result = instance.scalarMultiplication(constant);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Purely laziness in typing. This method will only display EXPECT and
     * RESULT matrices if not equal.
     *
     * @param expResult
     * @param result
     * @param scale
     */
    private void displayResultExpectIfNotEqual(Matrix expResult, Matrix result, int scale) {
        if (result.isEqualTo(expResult, scale) == false) {
            System.out.println("EXPECT\n" + expResult.toString());
            System.out.println("RESULT\n" + result.toString());
        }
    }

}
