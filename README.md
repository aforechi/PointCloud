# 3D Point Cloud Alignment Tool

This is a Java project that performs the following steps:
- Loads the provided point cloud
- Removes the current rotation, so the vertical axis of its body is aligned with the Y axis.
- Creates a new XYZ file with the transformed point cloud. The resulting point cloud should be in the same location in space.

## Point Clouds

A point cloud is set of 3D points in Cartesian coordinates for representing geometrical properties of objects. Point clouds are a common [map representation](https://link.springer.com/chapter/10.1007/978-3-030-91699-2_11) employed to store information about large site surveys, frequently using LIDAR sensors, such as in city-scale surveys for [Autonomous Vehicles](http://www.lcad.inf.ufes.br/wiki/index.php/IARA). In such scenarios, the increasing volume of information requires high performance libraries in order to manipulate the 3D data efficiently and accurately. Some examples are: [PCL](https://pointclouds.org), [GSL](https://www.gnu.org/software/gsl/), [Eigen](https://eigen.tuxfamily.org/index.php?title=Main_Page), [Alglib](https://www.alglib.net) in C++; [numpy](https://numpy.org) in Python, [JAMA](https://math.nist.gov/javanumerics/jama/) in Java and [Open3D](https://www.open3d.org/docs/latest/introduction.html) being the most comprehensive among all.

In the data folder you can find a file called [armadillo.xyz](./data/armadillo.xyz) which contains a point cloud with a 45 degree rotation in one of the axes.

## The Tool

The tool is based on GUI [Java App](src/main/java/pointcloud/Main.java) in order to improve the user experience for the MVP, including the automatic point cloud alignment feature. As this feature requires more advanced Linear Algebra methods there is a class [Matrix.java](src/main/java/pointcloud/Matrix.java) wrapped with bindings to [OpenCV library](https://docs.opencv.org/4.5.1/index.html).

The I/O functions are in a separate utility class [MatrixFile.java](src/main/java/pointcloud/MatrixFile.java) for clarity and organization. This helps in setting up the unit tests for Matrix and MatrixFile (see [MatrixFileTest.java](src/test/java/pointcloud/MatrixFileTest.java)) in a way that the point cloud data could be generated in-memory and does not require creating temporary files. There is a unit test class [MatrixTransformTest.java](src/test/java/pointcloud/MatrixTransformTest.java) for matrix transformations as well.

## The Alignment

Now, the transformation required for the Point Cloud alignment in this test is solved with the Principal Component Analysis (PCA). In the PCA method, the eigenvalues encodes the variance of points scattered in 3D and their corresponding eigenvectors represent the orientation. Once the eigenvectors associated with the highest eigenvalues were determined, the eigenvectors can be assembled column-wise to form a rotation matrix to carry out the transformation implemented in the alignPointCloudAlongVerticalAxis method of class [Matrix.java](src/main/java/pointcloud/Matrix.java).

For more details please find the Javadoc generated files [here](target/apidocs/index.html).

## Software Requirements
- Java 16
- JUnit 5
- Maven 4
- OpenCV 4

## Install

```
sudo add-apt-repository ppa:linuxuprising/java
sudo apt install openjdk-16-jdk
sudo apt install maven
```

## Usage

- Compile:
```
cd ./PointCloud/
mvn package
```

- Run:
```
cd ./PointCloud/
java -jar target/PointCloud-2.0-jar-with-dependencies.jar
```
or, alternatively
```
mvn exec:java -Dexec.mainClass="pointcloud.Main" 
```

- Test:
```
cd ./PointCloud
mvn clean test
```

- Docs:
```
cd ./PointCloud/
mvn install
```

## Disclaimer and limitations

This project has demonstrated the capability of reading a point cloud to memory in order to align the vertical body along with the y-axis and latter writing the resulting point cloud to disk without changing the body position in space. 

Although, it lacks semantic information to disambiguate the eigenvector direction based on the object type ([Airplane](./data/airplane.xyz), [Pearson](./data/human.xyz), [Guitar](./data/guitar.xyz)). This is specially relevant because eigenvectors computed by PCA are not uniquely defined due to [sign ambiguity](https://www.osti.gov/servlets/purl/920802).

This project has demonstrated the relevance of linear algebra for solving real world problems and the importance of OO Programming and Agile best practices, such as Unit Testing and Refactoring.

