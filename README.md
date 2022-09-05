# 3D Point Cloud Researcher test

This project is submitted as part of the application process for the Point Cloud Researcher position.

- Deadline: September 1st, 2022
- Submitted: August 28th, 2022
- Extension: September 5th, 2022

The goal of this test is to assess your technical skills, structuring projects and writing good code. You can take your time to do the test and then return the answers by email.

Before we start, just some context, for the following coding exercises you can assume they represent requests of Proof of Concepts (PoC) made by the product owner of the company and, they will be transformed into actual products and maintained/extended by other members of the team.

## Point cloud manipulation test

- Estimated time: 2h

In the provided zip file you can find a file called [armadillo.xyz](./data/armadillo.xyz) which contains a point cloud with a 45 degree rotation in one of the axes.

Please provide a Java project that performs the following steps:
- Loads the provided point cloud
- Removes the current rotation, so the vertical axis of its body is aligned with the Y axis.
- Creates a new XYZ file with the transformed point cloud. The resulting point cloud should be in the same location in space.

Please provide the Java code used to solve the proposed problem and relevant unit tests.

## My approach to solve the problem

A point cloud is set of 3D points in Cartesian coordinates for representing geometrical properties of objects. Another interesting definition is ["Point clouds are a means of collating a large number of single spatial measurements into a dataset that can then represent a whole"](https://info.vercator.com/blog/what-are-point-clouds-5-easy-facts-that-explain-point-clouds).

Point clouds are a common [map representation](https://link.springer.com/chapter/10.1007/978-3-030-91699-2_11) employed to store information about large site surveys, frequently using LIDAR sensors, such as in city-scale surveys for [Autonomous Vehicles](http://www.lcad.inf.ufes.br/wiki/index.php/IARA). In such scenarios, the increasing volume of information requires high performance libraries in order to manipulate the 3D data efficiently and accurately. Some examples are: [PCL](https://pointclouds.org), [GSL](https://www.gnu.org/software/gsl/), [Eigen](https://eigen.tuxfamily.org/index.php?title=Main_Page), [Alglib](https://www.alglib.net) in C++; [numpy](https://numpy.org) in Python and [JAMA](https://math.nist.gov/javanumerics/jama/) in Java to cite a few.

Considering this project is a PoC requested by the Product Owner (PO) I have redesigned it as GUI [Java App](src/main/java/com/vercator/Main.java) in order to improve the user experience for the PO, including the automatic point cloud alignment feature. As this feature requires more advanced Linear Algebra methods I have done a major refactoring and wrapped the class [Matrix.java](src/main/java/com/vercator/Matrix.java) with bindings to [OpenCV library](https://docs.opencv.org/4.5.1/index.html).

I decided to define the I/O functions in a separate utility class [MatrixFile.java](src/main/java/com/vercator/MatrixFile.java) for clarity and organization. This helps in setting up the unit tests for Matrix and MatrixFile (see [MatrixFileTest.java](src/test/java/com/vercator/MatrixFileTest.java)) in a way that the point cloud data could be generated in-memory and does not require creating temporary files. There is a unit test class [MatrixTransformTest.java](src/test/java/com/vercator/MatrixTransformTest.java) for matrix transformations as well.

Now, the transformation required for the Point Cloud alignment in this test is solved with the Principal Component Analysis (PCA). In the PCA method, the eigenvalues encodes the variance of points scattered in 3D and their corresponding eigenvectors represent the orientation. Once the eigenvectors associated with the highest eigenvalues were determined, the eigenvectors can be assembled column-wise to form a rotation matrix to carry out the transformation implemented in the alignPointCloudAlongVerticalAxis method of class [Matrix.java](src/main/java/com/vercator/Matrix.java).

For more details please find the Javadoc generated files [here](target/apidocs/index.html).

## Software Requirements
- Java 10
- JUnit 5
- Maven 4
- OpenCV 4

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
mvn exec:java -Dexec.mainClass="com.vercator.Main" 
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

## Conclusion and Future Works

This project has demonstrated the capability of reading a point cloud to memory in order to align the vertical body along with the y-axis and latter writing the resulting point cloud to disk without changing the body position in space. 

In the future, this project could be employed in conjunction with Machine Learning classification methods to detect objects in point clouds and help disambiguate the eigenvector direction based on the object type ([Airplane](./data/airplane.xyz), [Pearson](./data/human.xyz), [Guitar](./data/guitar.xyz)). This is specially relevant because eigenvectors computed by PCA are not uniquely defined due to [sign ambiguity](https://www.osti.gov/servlets/purl/920802).

This project has demonstrated the relevance of linear algebra for solving real world problems and the importance of OO Programming and Agile best practices, such as Unit Testing and Refactoring.


## Acknowledgments

I would like to thank Correvate for this opportunity and Vanhack for being so supportive.
