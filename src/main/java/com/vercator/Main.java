package com.vercator;

import nu.pattern.OpenCV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


/**
 * Main App Class
 */
public class Main {

    private final String inputFile;
    private final String outputFile;

    private JLabel inputLabel;
    private JLabel outputLabel;
    private JButton inputButton;
    private JButton outputButton;
    private JTextField inputText;
    private JTextField outputText;
    private JButton alignButton;
    private JCheckBox flipCheckbox;
    private JFrame frame;

    /**
     * Main App constructor
     * @param title window to display
     */
    public Main(String title) {
        inputFile = "./data/armadillo.xyz";
        outputFile = "./data/output.xyz";

        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame(title);
        frame.getContentPane().setLayout(new GridLayout(3,3));

        inputLabel = new JLabel("Input Filename:");
        frame.getContentPane().add(inputLabel);

        inputText = new JTextField();
        inputText.setToolTipText(inputFile);
        inputText.setText(inputFile);
        inputText.setBounds(50, 20, 200, 20);
        frame.getContentPane().add(inputText);

        inputButton = new JButton("Select file");
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser file = new JFileChooser(new File(inputText.getText()));
                file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int option = file.showOpenDialog(null);
                if (option==JFileChooser.APPROVE_OPTION){
                    inputText.setText(file.getSelectedFile().getAbsolutePath());
                }
            }
        });
        frame.getContentPane().add(inputButton);

        outputLabel = new JLabel("Output Filename:");
        frame.getContentPane().add(outputLabel);

        outputText = new JTextField();
        outputText.setToolTipText(outputFile);
        outputText.setText(outputFile);
        outputText.setBounds(50, 20, 200, 20);
        frame.getContentPane().add(outputText);

        outputButton = new JButton("Select file");
        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser file = new JFileChooser(new File(outputText.getText()));
                file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int option = file.showOpenDialog(null);
                if (option==JFileChooser.APPROVE_OPTION){
                    outputText.setText(file.getSelectedFile().getAbsolutePath());
                }
            }
        });
        frame.getContentPane().add(outputButton);

        flipCheckbox = new JCheckBox("Flip Upside Down");
        flipCheckbox.setHorizontalTextPosition(JCheckBox.LEFT);
        frame.getContentPane().add(flipCheckbox);
        frame.getContentPane().add(new JLabel(""));

        alignButton = new JButton("Align Point Cloud");
        alignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                alignPointCloud();
            }
        });
        frame.getContentPane().add(alignButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 120);
        frame.setVisible(true);
    }

    /**
     * main function loads OpenCV lib and open main window
     * @param args are not used
     */
    public static void main(String[] args) {
        OpenCV.loadLocally();
        new Main("Point Cloud Align Tool");
    }

    /**
     * The alignPointCloud function does:
     *  - Load a point cloud file with containing one 3D point per line. Each point is represented by x y z coordinates separated by spaces
     *  - Remove the current rotation, so the vertical axis of its body is aligned with the Y axis.
     *  - Create a new XYZ file with the transformed point cloud.
     *  The resulting point cloud should be in the same location in space.
     */
    private void alignPointCloud() {
        try {
            Matrix pointCloudInput = MatrixFile.readFile(inputText.getText());
            Matrix pointCloudOutput = pointCloudInput.alignPointCloudAlongVerticalAxis(flipCheckbox.isSelected());
            MatrixFile.writeFile(outputText.getText(), pointCloudOutput);
            System.out.println("Point Cloud was successfully written to the specified file.");
        }
        catch (IOException e) {
            System.err.println("Failed to save Point Cloud to the specified file.");
        }
    }

}