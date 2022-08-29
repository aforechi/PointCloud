package com.vercator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

class MatrixFileTest {

    @Test
    void readPointCloudFromStringTest() {
        Matrix expected = new Matrix(new double[][]{
                {0, 0, 0},
                {1, 1, 1},
                {1 / 2.0, 1 / 2.0, 1 / 2.0}
        });
        BufferedReader buffer = new BufferedReader(
                new StringReader("0 0 0" + System.lineSeparator() +
                                "1.0 1.0 1.0" + System.lineSeparator() +
                                "0.5 0.5 0.5" + System.lineSeparator()
                )
        );
        Matrix output = new Matrix(buffer.lines().toList());
        Assertions.assertAll("Should assert all items in the list are equal",
                () -> Assertions.assertArrayEquals(output.getRowVector(0), expected.getRowVector(0)),
                () -> Assertions.assertArrayEquals(output.getRowVector(1), expected.getRowVector(1)),
                () -> Assertions.assertArrayEquals(output.getRowVector(2), expected.getRowVector(2))
                );
    }

    @Test
    void writePointCloudToStringTest() {
        Matrix output = new Matrix(new double[][]{
                {0, 0, 0},
                {1, 1, 1},
                {1 / 2.0, 1 / 2.0, 1 / 2.0}
        });
        String expected = 0.0 + " " + 0.0 + " " + 0.0 + System.lineSeparator() +
                        1.0 + " " + 1.0 + " " + 1.0 + System.lineSeparator() +
                        1.0/2.0 + " " + 1.0/2.0 + " " + 1.0/2.0 + System.lineSeparator();
        Assertions.assertEquals(output.toString(), expected);
    }

    @Test
    void writeAndReadPointCloudTest() {
        Matrix expected = new Matrix(new double[][]{
                {0, 0, 0},
                {1, 1, 1},
                {1 / 2.0, 1 / 2.0, 1 / 2.0}
        });

        String pcString = expected.toString();
        Matrix output = new Matrix(new BufferedReader(new StringReader(pcString)).lines().toList());

        Assertions.assertAll("Should assert all items in the list are equal",
                () -> Assertions.assertArrayEquals(output.getRowVector(0), expected.getRowVector(0)),
                () -> Assertions.assertArrayEquals(output.getRowVector(1), expected.getRowVector(1)),
                () -> Assertions.assertArrayEquals(output.getRowVector(2), expected.getRowVector(2))
        );
    }

    @Test
    void readAndWritePointCloudTest() {
        String expected = "0.0 0.0 0.0" + System.lineSeparator() +
                "1.0 1.0 1.0" + System.lineSeparator() +
                "0.5 0.5 0.5" + System.lineSeparator();

        BufferedReader buffer = new BufferedReader( new StringReader( expected ) );
        String output = new Matrix(buffer.lines().toList()).toString();

        Assertions.assertEquals(expected, output);
    }
}