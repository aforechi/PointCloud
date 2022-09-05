package com.vercator;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.opencv.core.CvType.*;

/**
 * Simplified matrix class for handling point cloud transformations
 */
public class Matrix {
    private final Mat data;

    /**
     * Create a matrix of shape (rows, columns) and initializes the elements to the specified value.
     * - Negative rows and columns raise the NegativeArraySizeException.
     * - Zero rows or columns allow for empty matrix.
     * @param rows should be greater than zero
     * @param cols should be greater than zero
     * @param value initial value
     */
    public Matrix(int rows, int cols, double value) {
        data = new Mat(rows, cols, CV_64FC1);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                data.put(i, j, value);
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
        data = new Mat(rows, cols, CV_64FC1);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                data.put(i, j, values[i][j]);
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
        data = new Mat(rows, cols, CV_64FC1);
        for (int i=0; i < doubleArray.size(); i++) {
            for (int j=0; j < doubleArray.get(i).size(); j++) {
                data.put(i, j, doubleArray.get(i).get(j));
            }
        }
    }

    /**
     * Number of matrix rows
     * @return the size of first dimension
     */
    public int getRows() {
        return data.rows();
    }

    /**
     * Number of matrix columns
     * @return the size of second dimension
     */
    public int getColumns() {
        return data.cols();
    }

    /**
     * Row-vector
     * @param row is the row index ranging from zero to getRows()
     * @return an array of doubles with the elements in the specified row
     */
    public double[] getRowVector(int row) {
        double[] values = new double[getColumns()];
        for(int j=0; j < getColumns(); j++)
        {
            values[j] = data.get(row, j)[0];
        }
        return values;
    }

    /**
     * Format matrix content row-wise and to prevent java from creating its own version of toString.
     * @return a formatted string filled with matrix elements
     */
    public String toString() {
        StringBuilder text = new StringBuilder();
        for(int i = 0; i < getRows(); i++) {
            for(int j = 0; j < getColumns(); j++) {
                text.append(data.get(i, j)[0]);
                if (j < getColumns()-1)
                    text.append(" ");
            }
            text.append(System.lineSeparator());
        }
        return text.toString();
    }

    /**
     * Align the Point Cloud (data) according to the largest eigenvectors.
     * The largest aligns with y-axis, the second largest aligns with x-axis and last with the z-axis.
     * @param flipUpsideDown reverse the largest eigenvector sign.
     * @return the transformed Point Cloud
     */
    public Matrix alignPointCloudAlongVerticalAxis(boolean flipUpsideDown) {
        double sign = (flipUpsideDown ? -1.0 : 1.0);

        // centroid is 1x3 matrix of mean values in x,y,z
        Mat centroid = new Mat();
        // left eigenvectors is 3x3 matrix sorted in descending order
        Mat eigenvectors = new Mat();
        // eigenvalues is 3x1 vector sorted in descending order
        Mat eigenvalues = new Mat();

        Core.PCACompute2(data, centroid, eigenvectors, eigenvalues);

        //rotation matrix has shape of 3x3 assembled as follows:
        Mat rotation = new Mat(3, 3, CV_64FC1);
        //- first column corresponds to the 2nd largest eigenvector to align with the x-axis
        //- second column corresponds to the largest eigenvector to align with the y-axis
        //- third column corresponds to the smallest eigenvector to align with the z-axis
        for(int j=0; j < eigenvectors.cols(); j++){
            rotation.put(j, 0, eigenvectors.get(1, j)[0]);
            rotation.put(j, 1, sign * eigenvectors.get(0, j)[0]);
            rotation.put(j, 2, eigenvectors.get(2, j)[0]);
        }

        Mat normalizedPointCloud = new Mat(this.getRows(), this.getColumns(), CV_64FC1);
        Mat rotatedPointCloudT = new Mat(this.getColumns(), this.getRows(), CV_64FC1);
        for(int i=0; i < getRows(); i++) {
            Core.subtract(data.row(i), centroid, normalizedPointCloud.row(i));
        }
        Core.gemm(rotation.inv(), normalizedPointCloud.t(), 1, new Mat(), 0, rotatedPointCloudT);

        Mat rotatedPointCloud = rotatedPointCloudT.t();
        Mat alignedPointCloud = new Mat(this.getRows(), this.getColumns(), CV_64FC1);
        for(int i=0; i < getRows(); i++) {
            Core.add(rotatedPointCloud.row(i), centroid, alignedPointCloud.row(i));
        }

        Matrix output = new Matrix(getRows(), getColumns());
        alignedPointCloud.copyTo(output.data);
        return output;
    }
}
