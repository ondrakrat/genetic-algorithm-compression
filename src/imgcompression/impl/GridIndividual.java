package imgcompression.impl;

import imgcompression.ea.Individual;
import imgcompression.ea.functions.Fitness;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;

/**
 * @author Ondřej Kratochvíl
 */
@Data
public class GridIndividual implements Individual {

    private final Fitness<GridIndividual> fitnessFunction;  // TODO move to superclass, problems with generics though
    private final BufferedImage inputImage;

    private final int[][][] vertices;       // x and y coordinates
    private final int[][][] colours;        // RGB representation

    /**
     * Constructor for square grids.
     *
     * @param inputImage      input image
     * @param dimension       amount of tetragons per row
     * @param fitnessFunction fitness function to be used
     */
    public GridIndividual(BufferedImage inputImage, int dimension, Fitness<GridIndividual> fitnessFunction) {
        assert dimension > 0;
        this.fitnessFunction = fitnessFunction;
        this.inputImage = inputImage;
        this.vertices = new int[dimension + 1][dimension + 1][2];
        this.colours = new int[dimension][dimension][3];
    }

    /**
     * Constructor for rectangular grids.
     *
     * @param inputImage      input image
     * @param yDimension      amount of tetragons per column
     * @param xDimension      amount of tetragons per row
     * @param fitnessFunction fitness function to be used
     */
    public GridIndividual(BufferedImage inputImage,
                          int yDimension,
                          int xDimension, Fitness<GridIndividual> fitnessFunction) {
        assert xDimension > 0 && yDimension > 0;
        this.fitnessFunction = fitnessFunction;
        this.inputImage = inputImage;
        this.vertices = new int[yDimension + 1][xDimension + 1][2];
        this.colours = new int[yDimension][xDimension][3];
    }

    public int[] getVertex(int x, int y) {
        assert checkCoordinates(x, y, vertices);
        return vertices[x][y];
    }

    public int[] getPolygonColour(int x, int y) {
        assert checkCoordinates(x, y, colours);
        return colours[x][y];
    }

    public void setVertex(int x, int y, int xCoord, int yCoord) {
        assert checkCoordinates(x, y, vertices);
        assert xCoord >= 0 && xCoord < inputImage.getWidth();
        assert yCoord >= 0 && yCoord < inputImage.getHeight();
        vertices[x][y][0] = xCoord;
        vertices[x][y][1] = yCoord;
    }

    public void setVertex(int x, int y, int[] coords) {
        assert checkCoordinates(x, y, vertices);
        assert coords.length == 2;
        assert coords[0] >= 0 && coords[0] < inputImage.getWidth();
        assert coords[1] >= 0 && coords[1] < inputImage.getHeight();
        vertices[x][y] = coords;
    }

    public void setColour(int x, int y, int r, int g, int b) {
        assert checkCoordinates(x, y, colours);
        assert r >= 0 && r < 256;
        assert g >= 0 && g < 256;
        assert b >= 0 && b < 256;
        colours[x][y][0] = r;
        colours[x][y][1] = g;
        colours[x][y][2] = b;
    }

    public double getVertexXCoordinate(int x, int y) {
        return getVertex(x, y)[0];
    }

    public double getVertexYCoordinate(int x, int y) {
        return getVertex(x, y)[1];
    }

    /**
     * Check if the coordinates of an array are valid.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return {@code true} if the coordinates are valid, {@code false} otherwise
     */
    private boolean checkCoordinates(int x, int y, Object[][] array) {
        return x > 0 && y > 0 && x < array.length && y < array[0].length;
    }

    @Override
    public double fitness() {
        return fitnessFunction.applyAsDouble(this);
    }

    public void invalidateFitnessCache() {
        // TODO encapsulate
        fitnessFunction.invalidateCache();
    }

    public static boolean isCorner(int x, int y, int[][][] vertices) {
        return (x == 0 && y == 0)
                || (x == 0 && y == vertices[0].length - 1)
                || (x == vertices.length - 1 && y == 0)
                || (x == vertices.length - 1 && y == vertices[0].length - 1);
    }

    public static boolean isEdge(int x, int y, int[][][] vertices) {
        return x == 0 || y == 0 || (x == vertices.length - 1) || (y == vertices[0].length - 1);
    }
}
