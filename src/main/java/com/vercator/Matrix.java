package com.vercator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simplified matrix class for handling point cloud transformations
 */
public class Matrix {
    private final double[][] data;

    /**
     * Create a matrix of shape (rows, columns) and initializes the elements to the specified value.
     * - Negative rows and columns raise the NegativeArraySizeException.
     * - Zero rows or columns allow for empty matrix.
     * @param rows should be greater than zero
     * @param cols should be greater than zero
     * @param value initial value
     */
    public Matrix(int rows, int cols, double value) {
        data = new double[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                data[i][j] = value;
            }
        }
    }

    /**
     * Create a matrix of shape (rows, columns) and initializes the elements to 0.
     * - Negative rows and columns raise the NegativeArraySizeException.
     * - Zero rows or columns allow for empty matrix.
     * @param rows should be greater than zero
     * @param cols should be greater than zero
     */
    public Matrix(int rows, int cols) {
        this(rows, cols, 0.0);
    }

    /**
     * Create a matrix filled with values
     * @param values are the matrix elements
     */
    public Matrix(double[][] values) {
        int rows = values.length;
        int cols = values[0].length;
        data = new double[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                data[i][j] = values[i][j];
            }
        }
    }

    /**
     * Import matrix from a list of string format
     * @param stringArray is a list of rows containing numbers in string format
     */
    public Matrix(List<String> stringArray) {
        List<List<Double>> doubleArray = new ArrayList<>();
        for (String row : stringArray) {
            List<Double> list = Stream.of(row.split(" "))
                    .map(Double::valueOf)
                    .collect(Collectors.toList());
            doubleArray.add(list);
        }
        int rows = doubleArray.size();
        int cols = doubleArray.get(0).size();
        data = new double[rows][cols];
        for (int i=0; i < doubleArray.size(); i++) {
            for (int j=0; j < doubleArray.get(i).size(); j++) {
                data[i][j] = doubleArray.get(i).get(j);
            }
        }
    }

    /**
     * Number of matrix rows
     * @return the size of first dimension
     */
    public int getRows() {
        return data.length;
    }

    /**
     * Number of matrix columns
     * @return the size of second dimension
     */
    public int getColumns() {
        return data[0].length;
    }

    /**
     * Row-vector
     * @param row is the row index ranging from zero to getRows()
     * @return an array of doubles with the elements in the specified row
     */
    public double[] getRowVector(int row) {
        return data[row];
    }

    /**
     * Matrix-Matrix product A.B=C
     * Multiply the matrix A (this) by B.
     * A (this) is a n by k matrix
     * @param B is a k by m matrix
     * @return the resulting n by m matrix C
     */
    public Matrix multiply(Matrix B){
        if (this.getColumns() != B.getRows())
            throw new RuntimeException("Matrices dimensions do not agree");
        Matrix C = new Matrix(this.getRows(), B.getColumns());
        for(int i=0; i<C.getRows(); i++){
            for (int j=0; j<C.getColumns(); j++){
                for (int k=0; k<this.getColumns(); k++){
                    C.data[i][j] += this.data[i][k] * B.data[k][j];
                }
            }
        }
        return C;
    }

    /**
     * Transpose Operation
     * @return a matrix with transposed rows and columns
     */
    public Matrix transpose(){
        Matrix T = new Matrix(this.getColumns(), this.getRows());
        for (int i=0; i<this.getRows(); i++){
            for (int j=0; j<this.getColumns(); j++){
                T.data[j][i] = this.data[i][j];
            }
        }
        return T;
    }

    /**
     * Concatenate row at the end
     * @param value used to fill the new row elements
     * @return a matrix with an additional row filled with the specified value
     */
    public Matrix addRow(double value){
        Matrix H = new Matrix(this.getRows()+1, this.getColumns(), value);
        for (int i=0; i<this.getRows(); i++){
            for (int j=0; j<this.getColumns(); j++){
                H.data[i][j] = this.data[i][j];
            }
        }
        return H;
    }

    /**
     * Remove row at the end
     * @return a matrix without the original last row
     */
    public Matrix popRow(){
        Matrix H = new Matrix(this.getRows()-1, this.getColumns());
        for (int i=0; i<this.getRows()-1; i++){
            for (int j=0; j<this.getColumns(); j++){
                H.data[i][j] = this.data[i][j];
            }
        }
        return H;
    }

    /**
     * Format matrix content row-wise and to prevent java from creating its own version of toString.
     * @return a formatted string filled with matrix elements
     */
    public String toString() {
        StringBuilder text = new StringBuilder();
        for(int i = 0; i < getRows(); i++) {
            for(int j = 0; j < getColumns(); j++) {
                text.append(data[i][j]);
                if (j < getColumns()-1)
                    text.append(" ");
            }
            text.append(System.lineSeparator());
        }
        return text.toString();
    }

}
