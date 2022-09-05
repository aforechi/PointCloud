package com.vercator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Read and write a point cloud to text files
 */
final class MatrixFile {

    /**
     * Read CSV file format using blank spaces instead of commas
     * @param filename path and name of input file
     * @return a matrix
     * @throws FileNotFoundException when attempting to open a non-existing file
     */
    public static Matrix readFile(String filename) throws IOException {
        return new Matrix(Files.readAllLines(Paths.get(filename)));
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
