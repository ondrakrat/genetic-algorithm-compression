package imgcompression.ga;

import lombok.Data;

/**
 * @author Ondřej Kratochvíl
 */
@Data
public class GridIndividual {

    private final double[][][] vertices;    // x and y coordinates
    private final int[][][] colours;        // RGB representation

    /**
     * Constructor for square grids.
     *
     * @param dimension amount of tetragons per row
     */
    public GridIndividual(int dimension) {
        assert dimension > 0;
        this.vertices = new double[dimension + 1][dimension + 1][2];
        this.colours = new int[dimension][dimension][3];
    }

    /**
     * Constructor for rectangular grids.
     *
     * @param xDimension amount of tetragons per row
     * @param yDimension amount of tetragons per column
     */
    public GridIndividual(int xDimension, int yDimension) {
        assert xDimension > 0 && yDimension > 0;
        this.vertices = new double[xDimension + 1][yDimension + 1][2];
        this.colours = new int[xDimension][yDimension][3];
    }

    public double[] getVertex(int x, int y) {
        assert checkCoordinates(x, y, vertices);
        return vertices[x][y];
    }

    public int[] getVertexColour(int x, int y) {
        assert checkCoordinates(x, y, colours);
        return colours[x][y];
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
        // TODO check x/y values on the edges of the pictures (need to be 0 or width/height) - only for setters though
        return x > 0 && y > 0 && x < array.length && y < array[0].length;
    }
}
