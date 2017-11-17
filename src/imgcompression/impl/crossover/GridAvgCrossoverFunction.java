package imgcompression.impl.crossover;

import imgcompression.ea.functions.Crossover;
import imgcompression.impl.GridIndividual;

import static imgcompression.helper.GraphicHelper.*;

/**
 * @author Ondřej Kratochvíl
 */
public class GridAvgCrossoverFunction implements Crossover<GridIndividual> {

    @Override
    public GridIndividual apply(GridIndividual individual, GridIndividual individual2) {
        GridIndividual child = new GridIndividual(
                individual.getInputImage(),
                individual.getVertices().length,
                individual.getVertices()[0].length,
                individual.getFitnessFunction());
        for (int i = 0; i < child.getVertices().length; i++) {
            for (int j = 0; j < child.getVertices()[0].length; ++j) {
                // combine vertices
                int[] vertex1 = individual.getVertex(i, j);
                int[] vertex2 = individual2.getVertex(i, j);
                if (isCorner(i, j, child.getVertices())) {
                    child.setVertex(i, j, individual.getVertex(i, j));  // doesn't matter which individual is used
                } else {
                    // combining edges should remain the same - one of the coordinates should be equal for both individuals
                    int[] newCoords = new int[2];
                    newCoords[0] = (vertex1[0] + vertex2[0]) / 2;
                    newCoords[1] = (vertex1[1] + vertex2[1]) / 2;
                    child.setVertex(i, j, newCoords);
                }

                // combine colour
                if (i < child.getColours().length && j < child.getColours()[0].length) {
                    int[] colour1 = individual.getPolygonColour(i, j);
                    int[] colour2 = individual2.getPolygonColour(i, j);
                    int color1ARGB = convertToARGB(colour1[0], colour1[1], colour1[2]);
                    int color2ARGB = convertToARGB(colour2[0], colour2[1], colour2[2]);
                    int mixedColour = mixColour(color1ARGB, color2ARGB);
                    child.setColour(i, j, getRed(mixedColour), getGreen(mixedColour), getBlue(mixedColour));
                }
            }
        }
        return child;
    }

    private boolean isCorner(int x, int y, int[][][] vertices) {
        return (x == 0 && y == 0)
                || (x == 0 && y == vertices[0].length)
                || (x == vertices.length && y == 0)
                || (x == vertices.length && y == vertices[0].length);
    }
}
