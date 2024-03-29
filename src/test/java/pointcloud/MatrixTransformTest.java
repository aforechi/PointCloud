package pointcloud;

import nu.pattern.OpenCV;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class MatrixTransformTest {
    @BeforeAll
    static void setUp() {
        OpenCV.loadLocally();
    }

    @Test
    void alignArmadilloTest() throws IOException {
        List<String> file1 = Files.readAllLines(Paths.get("./data/armadillo.xyz"));
        List<String> file2 = Files.readAllLines(Paths.get("./data/armadillo_up.xyz"));

        Matrix mat1 = new Matrix(file1);
        Matrix mat2 = new Matrix(file2);

        Assertions.assertEquals(mat1.getRows() * mat1.getColumns(), mat2.getRows() * mat2.getColumns());

        Matrix output = mat1.alignPointCloudAlongVerticalAxis(false);
        for (int i = 0; i < mat1.getRows(); i++) {
            Assertions.assertArrayEquals(output.getRowVector(i), mat2.getRowVector(i), 1e-8);
        }
    }

    @Test
    void alignAirplaneTest() throws IOException {
        List<String> file1 = Files.readAllLines(Paths.get("./data/airplane.xyz"));
        List<String> file2 = Files.readAllLines(Paths.get("./data/airplane_up.xyz"));

        Matrix mat1 = new Matrix(file1);
        Matrix mat2 = new Matrix(file2);

        Assertions.assertEquals(mat1.getRows() * mat1.getColumns(), mat2.getRows() * mat2.getColumns());

        Matrix output = mat1.alignPointCloudAlongVerticalAxis(true);
        for (int i = 0; i < mat1.getRows(); i++) {
            Assertions.assertArrayEquals(output.getRowVector(i), mat2.getRowVector(i), 1e-8);
        }
    }

    @Test
    void alignHumanTest() throws IOException {
        List<String> file1 = Files.readAllLines(Paths.get("./data/human.xyz"));
        List<String> file2 = Files.readAllLines(Paths.get("./data/human_up.xyz"));

        Matrix mat1 = new Matrix(file1);
        Matrix mat2 = new Matrix(file2);

        Assertions.assertEquals(mat1.getRows() * mat1.getColumns(), mat2.getRows() * mat2.getColumns());

        Matrix output = mat1.alignPointCloudAlongVerticalAxis(false);
        for (int i = 0; i < mat1.getRows(); i++) {
            Assertions.assertArrayEquals(output.getRowVector(i), mat2.getRowVector(i), 1e-8);
        }
    }

    @Test
    void alignGuitarTest() throws IOException {
        List<String> file1 = Files.readAllLines(Paths.get("./data/guitar.xyz"));
        List<String> file2 = Files.readAllLines(Paths.get("./data/guitar_up.xyz"));

        Matrix mat1 = new Matrix(file1);
        Matrix mat2 = new Matrix(file2);

        Assertions.assertEquals(mat1.getRows() * mat1.getColumns(), mat2.getRows() * mat2.getColumns());

        Matrix output = mat1.alignPointCloudAlongVerticalAxis(false);
        for (int i = 0; i < mat1.getRows(); i++) {
            Assertions.assertArrayEquals(output.getRowVector(i), mat2.getRowVector(i), 1e-8);
        }
    }

}