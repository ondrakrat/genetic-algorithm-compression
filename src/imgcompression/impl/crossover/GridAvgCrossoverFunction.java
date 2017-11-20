package imgcompression.impl.crossover;

import imgcompression.ea.functions.Crossover;
import imgcompression.impl.GridIndividual;

import java.util.Arrays;

import static imgcompression.helper.GraphicHelper.*;
import static imgcompression.impl.GridIndividual.isCorner;

/**
 * @author Ondřej Kratochvíl
 */
public class GridAvgCrossoverFunction implements Crossover<GridIndividual> {

    @Override
    public GridIndividual apply(GridIndividual individual, GridIndividual individual2) {
        GridIndividual child = new GridIndividual(
                individual.getInputImage(),
                individual.getColours().length,
                individual.getColours()[0].length,
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
                    newCoords[0] = Math.toIntExact(Math.round((vertex1[0] + vertex2[0]) / 2.0));
                    newCoords[1] = Math.toIntExact(Math.round((vertex1[1] + vertex2[1]) / 2.0));
                    child.setVertex(i, j, newCoords);
                }

                // combine colour
                if (i < child.getColours().length && j < child.getColours()[0].length) {
                    int xIndex = Math.min(i, individual.getColours().length - 1);
                    int yIndex = Math.min(j, individual.getColours()[0].length - 1);
                    int[] colour1 = individual.getPolygonColour(xIndex, yIndex);
                    int[] colour2 = individual2.getPolygonColour(xIndex, yIndex);
                    int color1ARGB = convertToARGB(colour1[0], colour1[1], colour1[2]);
                    int color2ARGB = convertToARGB(colour2[0], colour2[1], colour2[2]);
                    int mixedColour = mixColour(color1ARGB, color2ARGB);
                    child.setColour(i, j, getRed(mixedColour), getGreen(mixedColour), getBlue(mixedColour));
                }
            }
        }
//        checkConsistency(child);
        return child;
    }

    private void checkConsistency(GridIndividual child) {
        for (int i = 0; i < child.getVertices().length; i++) {
            for (int j = 0; j < child.getVertices()[0].length; ++j) {
                if (j > 0 && child.getVertex(i, j)[0] < child.getVertex(i, j - 1)[0]) {
                    System.out.println("X coordinate inconsistence, elements: "
                            + Arrays.toString(child.getVertex(i, j - 1)) + ", " + Arrays.toString(child.getVertex(i, j)));
                }
                if (i > 0 && child.getVertex(i, j)[1] < child.getVertex(i - 1, j)[1]) {
                    System.out.println("Y coordinate inconsistence, elements: "
                            + Arrays.toString(child.getVertex(i - 1, j)) + ", " + Arrays.toString(child.getVertex(i, j)));
                }
            }
        }
    }
}
