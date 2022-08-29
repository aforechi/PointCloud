package com.vercator;

/**
 * Pipeline Design Pattern applied to matrix transformations
 */
interface Handler {
    Matrix process(Matrix input);
}

/**
 * Rotate matrix according to RHS convention around the x-axis.
 */
class RotateX implements Handler {
    private double ax;
    private Matrix Rx;

    /**
     * Initialise the 3D rotation matrix
     * @param angle in degrees
     */
    public RotateX(double angle) {
        ax = angle * (Math.PI / 180.);
        Rx = new Matrix(new double[][]{
                {1,0.0,0.0,0.0},
                {0.0,Math.cos(ax),-Math.sin(ax),0.0},
                {0.0,Math.sin(ax),Math.cos(ax),0.0},
                {0.0,0.0,0.0,1.0}}
        );
    }

    /**
     * Apply the rotation around the x-axis.
     * @param input is matrix of shape 4xN
     * @return the transformed matrix
     */
    @Override
    public Matrix process(Matrix input) {
        return Rx.multiply(input);
    }
}

/**
 * Rotate matrix according to RHS convention around y-axis.
 */
class RotateY implements Handler {
    private double ay;
    private Matrix Ry;

    /**
     * Initialise the 3D rotation matrix
     * @param angle in degrees
     */
    public RotateY(double angle) {
        ay = angle * (Math.PI / 180.);
        Ry = new Matrix(new double[][]{
                {Math.cos(ay),0.0,Math.sin(ay),0.0},
                {0.0,1.0,0.0,0.0},
                {-Math.sin(ay),0.0,Math.cos(ay),0.0},
                {0.0,0.0,0.0,1.0}}
        );
    }

    /**
     * Apply the rotation around the y-axis.
     * @param input is matrix of shape 4xN
     * @return the transformed matrix
     */
    @Override
    public Matrix process(Matrix input) {
        return Ry.multiply(input);
    }
}

/**
 * Rotate matrix according to RHS convention around z-axis.
 */
class RotateZ implements Handler {
    private double az;
    private Matrix Rz;

    /**
     * Initialise the 3D rotation matrix
     * @param angle in degrees
     */
    public RotateZ(double angle) {
        az = angle * (Math.PI / 180.);
        Rz = new Matrix(new double[][]{
                {Math.cos(az),-Math.sin(az),0.0,0.0},
                {Math.sin(az),Math.cos(az),0.0,0.0},
                {0.0,0.0,1.0,0.0},
                {0.0,0.0,0.0,1.0}}
        );
    }

    /**
     * Apply the rotation around the z-axis.
     * @param input is matrix of shape 4xN
     * @return the transformed matrix
     */
    @Override
    public Matrix process(Matrix input) {
        return Rz.multiply(input);
    }
}

/**
 * Transpose matrix transformation
 */
class Transpose implements Handler {

    /**
     * Transpose rows and columns.
     * @param input is matrix of shape NxM
     * @return the transposed matrix of shape MxN
     */
    @Override
    public Matrix process(Matrix input) {
        return input.transpose();
    }
}

/**
 * Concatenate a row at the end of a matrix
 */
class AddRowAtTheEnd implements Handler {

    private double value;

    /**
     * Initialise the value of the elements in the new added row
     * @param value
     */
    public AddRowAtTheEnd(double value){
        this.value = value;
    }

    /**
     * Add a row with all elements equal to the specified value.
     * @param input is matrix of shape NxM
     * @return the transformed matrix of shape (N+1)xM
     */
    @Override
    public Matrix process(Matrix input) {
        return input.addRow(value);
    }
}

/**
 * Eliminate the last row of a matrix
 */
class DelRowAtTheEnd implements Handler {

    /**
     * Remove the last row of the input matrix.
     * @param input is matrix of shape NxM
     * @return the transformed matrix of shape (N-1)xM
     */
    @Override
    public Matrix process(Matrix input) {
        return input.popRow();
    }
}

class Pipeline {

    private final Handler currentHandler;

    Pipeline(Handler currentHandler) {
        this.currentHandler = currentHandler;
    }

    Pipeline addHandler(Handler newHandler) {
        return new Pipeline(input -> newHandler.process(currentHandler.process(input)));
    }

    Matrix execute(Matrix input) {
        return currentHandler.process(input);
    }
}

/**
 * Useful Transforms for Point Cloud data
 */
public final class MatrixTransform {

    /**
     * Rotate the Point Cloud according to RHS convention in the x-axis, y-axis and lastly the z-axis.
     * @param pointCloud is a list of 3D points
     * @param rx rotation angle in degrees around the x-axis.
     * @param ry rotation angle in degrees around the y-axis.
     * @param rz rotation angle in degrees around the z-axis.
     * @return the transformed Point Cloud
     */
    public static Matrix rotate(Matrix pointCloud, double rx, double ry, double rz){

        var filters = new Pipeline(new Transpose())
                .addHandler(new AddRowAtTheEnd(1));

        if (rx != 0)
            filters = filters.addHandler(new RotateX(rx));
        if (ry != 0)
            filters = filters.addHandler(new RotateY(ry));
        if (rz != 0)
            filters = filters.addHandler(new RotateZ(rz));

        filters = filters.addHandler(new DelRowAtTheEnd())
                .addHandler(new Transpose());

        return filters.execute(pointCloud);

    }
}
