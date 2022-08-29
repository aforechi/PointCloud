package com.vercator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatrixTransformTest {

    @Test
    void multiplyTest() {
        Matrix A = new Matrix(new double[][] {
                {2,	3, -4,	1},
                {11,8,	7,	0},
                {2,	5,	3,	1},
                {0,	0,	0,  1}}
        );
        Matrix B = new Matrix(new double[][] {{3, 7, 5, 1}});
        Matrix output = A.multiply(B.transpose());
        double[] target = {8, 124, 57, 1};

        Assertions.assertArrayEquals(target, output.transpose().getRowVector(0));
    }

    @Test
    void rotateXTest() {
        Matrix input = new Matrix(new double[][]{{1,1,1}});
        Matrix target = new Matrix(new double[][]{{1,-0.3660254038,1.366025404}});

        Matrix output = MatrixTransform.rotate(input, 60, 0, 0);
        Assertions.assertArrayEquals(output.getRowVector(0), target.getRowVector(0), 1e-8);
    }

    @Test
    void rotateYTest() {
        Matrix input = new Matrix(new double[][]{{1,1,1}});
        Matrix target = new Matrix(new double[][]{{1,1,-1}});

        Matrix output = MatrixTransform.rotate(input, 0, 90, 0);
        Assertions.assertArrayEquals(output.getRowVector(0), target.getRowVector(0), 1e-8);
    }

    @Test
    void rotateZTest() {
        Matrix input = new Matrix(new double[][]{{1,1,1}});
        Matrix target = new Matrix(new double[][]{{-1,1,1}});

        Matrix output = MatrixTransform.rotate(input, 0, 0, 90);
        Assertions.assertArrayEquals(output.getRowVector(0), target.getRowVector(0), 1e-8);
    }

    @Test
    void rotateXYTest() {
        Matrix input = new Matrix(new double[][]{{1,1,1}});
        Matrix target = new Matrix(new double[][]{{1.673032608,	-0.3660254038,0.2588190451}});

        Matrix output = MatrixTransform.rotate(input, 60, 45, 0);
        Assertions.assertArrayEquals(output.getRowVector(0), target.getRowVector(0), 1e-8);
    }

    @Test
    void rotateXZTest() {
        Matrix input = new Matrix(new double[][]{{1,1,1}});
        Matrix target = new Matrix(new double[][]{{1.573132185,0.724744871,0}});

        Matrix output = MatrixTransform.rotate(input, -45, 0, -30);
        Assertions.assertArrayEquals(output.getRowVector(0), target.getRowVector(0), 1e-8);
    }

    @Test
    void rotateYZTest() {
        Matrix input = new Matrix(new double[][]{{1,1,1}});
        Matrix target = new Matrix(new double[][]{{0.5,0.8660254038,1.414213562}});

        Matrix output = MatrixTransform.rotate(input, 0, -45, -30);
        Assertions.assertArrayEquals(output.getRowVector(0), target.getRowVector(0), 1e-8);
    }

    @Test
    void rotateXYZTest() {
        Matrix input = new Matrix(new double[][]{{1,1,1}});
        Matrix target = new Matrix(new double[][]{{1.631901441,	0.5195290056,	0.2588190451}});

        Matrix output = MatrixTransform.rotate(input, 60, 45, 30);
        Assertions.assertArrayEquals(output.getRowVector(0), target.getRowVector(0), 1e-8);
    }

}