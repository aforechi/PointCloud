# 3D Point Cloud Researcher test

This project is submitted as part of the application process for the Point Cloud Researcher position.

- Deadline: September 1st, 2022
- Submitted: August 28th, 2022

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

Point clouds are a common map representation employed to store information about large site surveys, frequently using LIDAR sensors, such as in city-scale surveys for [Autonomous Vehicles](http://www.lcad.inf.ufes.br/wiki/index.php/IARA). In such scenarios, the increasing volume of information requires high performance libraries in order to manipulate the 3D data efficiently and accurately. Some examples are: [PCL](https://pointclouds.org), [GSL](https://www.gnu.org/software/gsl/), [Eigen](https://eigen.tuxfamily.org/index.php?title=Main_Page), [Alglib](https://www.alglib.net) in C++; [numpy](https://numpy.org) in Python and [JAMA](https://math.nist.gov/javanumerics/jama/) in Java to cite a few.

Considering this project is defined in the assignment test as a PoC I have decided to implement a tailored-specific Matrix class with the minimum necessary operations to this task as it is common practice in [homework assignments](https://github.com/aforechi/ifes-alg-2018-1/blob/master/trabalho.ipynb). The design of the [Matrix.java](src/main/java/com/vercator/Matrix.java) follows the best practices of Object-Oriented Programming and can be easily replaced in the future.

I decided to define the I/O functions in a separate utility class [MatrixFile.java](src/main/java/com/vercator/MatrixFile.java) for clarity and organization. This helps in setting up the unit tests for Matrix and MatrixFile (see [MatrixFileTest.java](src/test/java/com/vercator/MatrixFileTest.java)) in a way that point cloud data could be generated in-memory and not required creating temporary files. There is a unit test class [MatrixTransformTest.java](src/test/java/com/vercator/MatrixTransformTest.java) for matrix transformations as well.

Now, the transformations required for Point Cloud manipulation for this test are a combination of 3D rotation matrices around the x-y-z axis. Considering the combinations of transformations can vary according to the object position and orientation in the Point Cloud I have chosen the [Pipeline Design Pattern](https://java-design-patterns.com/patterns/pipeline/) for representing the pipeline of transformation in class [MatrixTransform.java](src/main/java/com/vercator/MatrixTransform.java) according to user needs specified by command line arguments in class [Main.java](src/main/java/com/vercator/Main.java).

For more details please find the Javadoc generated files [here](doc/index.html).

## Software Requirements
- Java 10
- JUnit 5
- Maven 4
- OpenCV 4

## Usage

- Test:
```
cd ./PointCloud
mvn clean test
```

- Run:
```
cd ./PointCloud/
mvn exec:java -Dexec.mainClass="com.vercator.Main" -Dexec.args="--input ./data/armadillo.xyz --output ./data/output.xyz"
```


## Conclusion and Future Works

This project has demonstrated the capability of reading a point cloud to memory in order to rotate around x-y-z axis by pre-defined angle in degree for latter writing the resulting point cloud to disk. 

This project could be employed in conjunction with another project that detects objects in point clouds and automatically determine the rotation of the body w.r.t. the vertical axis y. The segmentation of point clouds could be investigated using Deep Learning algorithms and the orientation angle of the object could be determined by Principal Component Analysis (PCA).

In the PCA method, the biggest eigenvector aligns with the axis where the points are widely spread. There are many applications of this technique in Computer Vision, but cite one that might be relevant for removing pedestrian trace from point cloud data I would suggest reading this [paper](http://www.ijmerr.com/uploadfile/2020/0417/20200417064018858.pdf). Eigenvectors computed by PCA are not uniquely defined due to sign ambiguity. PCA supports fast ad-hoc “sign flip” technique described in the paper [Bro07](https://www.osti.gov/servlets/purl/920802).

This project has demonstrated the relevance of linear algebra for solving real world problems and the importance of OO Programming and Design Patterns towards the motto "Coding for Humanity" as my former advisor used to say.


## Acknowledgments

I would like to greatly thank Correvate for this opportunity and Vanhack for being so supportive.
