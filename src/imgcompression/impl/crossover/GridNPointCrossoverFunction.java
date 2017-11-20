package imgcompression.impl.crossover;

import imgcompression.ea.functions.Crossover;
import imgcompression.impl.GridIndividual;

/**
 * @author Ondřej Kratochvíl
 */
public class GridNPointCrossoverFunction implements Crossover<GridIndividual> {

    @Override
    public GridIndividual apply(GridIndividual individual, GridIndividual individual2) {
        int[][][] vertices = individual.getVertices();
        int[][][] colours = individual.getColours();
        GridIndividual child = new GridIndividual(individual.getInputImage(),
                colours.length, colours[0].length, individual.getFitnessFunction());
        child.invalidateFitnessCache();
        for (int i = 0; i < vertices.length; ++i) {
            for (int j = 0; j < vertices[0].length; ++j) {
                child.setVertex(i, j, vertices[i][j]);
                if (i < child.getColours().length && j < child.getColours()[0].length) {
                    if (j % 2 == 0) {
                        int[] polygonColour = individual.getPolygonColour(i, j);
                        child.setColour(i, j, polygonColour[0], polygonColour[1], polygonColour[2]);
                    } else {
                        int[] polygonColour = individual2.getPolygonColour(i, j);
                        child.setColour(i, j, polygonColour[0], polygonColour[1], polygonColour[2]);
                    }
                }
            }
        }
        return child;
    }
}
