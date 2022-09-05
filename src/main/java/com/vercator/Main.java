package com.vercator;

import nu.pattern.OpenCV;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main Program Class
 */
public class Main {
    static boolean flipUpsideDown;
    static String inputFile;
    static String outputFile;
    static final Map<String, List<String>> params = new HashMap<>();
    static final String USAGE_MESSAGE = "\n --input ./data/armadillo.xyz --output ./data/output.xyz";

    /**
     * TODO: Create a Java Applet for the user (Product Owner)
     * The main function does:
     *  - Load a point cloud file with containing one 3D point per line. Each point is represented by x y z coordinates separated by spaces
     *  - Remove the current rotation, so the vertical axis of its body is aligned with the Y axis.
     *  - Create a new XYZ file with the transformed point cloud.
     *  The resulting point cloud should be in the same location in space.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        OpenCV.loadLocally();
        parseCommandLineArguments(args);
        try {
            Matrix pointCloudInput = MatrixFile.readFile(inputFile);
            Matrix pointCloudOutput = pointCloudInput.alignPointCloudAlongVerticalAxis(flipUpsideDown);
            MatrixFile.writeFile(outputFile, pointCloudOutput);
            System.out.println("Point Cloud was successfully written to the specified file.");
        }
        catch (IOException e) {
            System.err.println("Failed to save Point Cloud to the specified file.");
        }
    }

    /**
     * Parse command line arguments to extract the input and output filenames.
     * Optionally, you can choose to flip the point cloud upside down by passing --flip 1.
     * @param args is command line arguments
     * @throws RuntimeException when the arguments are not correctly fulfilled
     */
    private static void parseCommandLineArguments(String[] args) throws RuntimeException {
        List<String> options = null;

        if (args.length == 0)
            throw new RuntimeException("Please see correct usage " + USAGE_MESSAGE);

        for (int i = 0; i < args.length; i++) {
            final String a = args[i];

            if (a.startsWith("--")) {
                if (a.length() == 2) {
                    throw new RuntimeException("Error at argument " + a + USAGE_MESSAGE);
                }

                options = new ArrayList<>();
                params.put(a.substring(2), options);
            }
            else if (options != null) {
                options.add(a);
            }
            else {
                throw new RuntimeException("Incomplete argument " + a + USAGE_MESSAGE);
            }
        }

        inputFile = params.get("input").get(0);
        outputFile = params.get("output").get(0);
        if (params.get("flip") != null && params.get("flip").size() >= 1) {
            if ("1".equals(params.get("flip").get(0))) {
                flipUpsideDown = true;
            }
            else {
                flipUpsideDown = false;
            }
        }
        else {
            flipUpsideDown = false;
        }
    }
}