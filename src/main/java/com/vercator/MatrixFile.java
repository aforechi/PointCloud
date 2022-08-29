package com.vercator;

import java.io.*;

/**
 * Read and write a point cloud to a text file
 */
public final class MatrixFile {

    /**
     * Read CSV file format using blank spaces instead of commas
     * @param filename path and name of input file
     * @return a matrix
     * @throws FileNotFoundException when attempting to open a non-existing file
     */
    public static Matrix readFile(String filename) throws FileNotFoundException {
        return new Matrix(new BufferedReader(new FileReader(new File(filename))).lines().toList());
    }


    /**
     * Write a list of points to a text file using the CSV file format with a blank space instead of a comma
     * @param filename path and name of the output file
     * @param pointCloud list of points
     * @throws IOException when fails to write a file
     */
    public static void writeFile(String filename, Matrix pointCloud) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write(pointCloud.toString());
        fileWriter.close();
    }
}
