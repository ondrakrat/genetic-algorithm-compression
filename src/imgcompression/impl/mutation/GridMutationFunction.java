package imgcompression.impl.mutation;

import imgcompression.ea.functions.Mutation;
import imgcompression.impl.GridIndividual;

import java.util.concurrent.ThreadLocalRandom;

import static imgcompression.impl.GridIndividual.isCorner;
import static imgcompression.impl.GridIndividual.isEdge;

/**
 * @author Ondřej Kratochvíl
 */
public class GridMutationFunction implements Mutation<GridIndividual> {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final double mutationChance;
    private final double mutationExtent;

    public GridMutationFunction(double mutationChance, double mutationExtent) {
        this.mutationChance = mutationChance;
        this.mutationExtent = mutationExtent;
    }

    @Override
    public GridIndividual apply(GridIndividual individual) {
//        mutateVertexPosition(individual);
        mutatePolygonColour(individual);
        individual.invalidateFitnessCache();
        return individual;
    }

    private boolean shouldMutate() {
        return random.nextDouble() < mutationChance;
    }

    private void mutateVertexPosition(GridIndividual individual) {
        int[] bottomRight = individual.getVertex(individual.getVertices().length - 1,
                individual.getVertices()[0].length - 1);
        int xMutationDelta = (int) (bottomRight[0] * mutationExtent);
        int yMutationDelta = (int) (bottomRight[1] * mutationExtent);
        int previousX = 0;
        int previousY = 0;
        int nextX, nextY;
        for (int i = 0; i < individual.getVertices().length; ++i) {
            for (int j = 0; j < individual.getVertices()[0].length; ++j) {
                if (!isCorner(i, j, individual.getVertices()) && shouldMutate()) {
                    int[] vertex = individual.getVertex(i, j);
                    nextX = (j == individual.getVertices()[0].length - 1)
                            ? bottomRight[0] : individual.getVertex(i, j + 1)[0];
                    nextY = (i == individual.getVertices().length - 1)
                            ? bottomRight[1] : individual.getVertex(i + 1, j)[1];

                    if (isEdge(i, j, individual.getVertices())) {
//                        int xCoord, yCoord;
//                        if (vertex[0] == 0 || vertex[0] == bottomRight[0]) {    // top or bottom edge
//                            if (Math.max(previousY, vertex[1] - yMutationDelta) >= Math.min(nextY, vertex[1] + yMutationDelta)) {
//                                yCoord = Math.max(previousY, vertex[1] - yMutationDelta);
//                            } else {
//                                yCoord = random.nextInt(Math.max(previousY, vertex[1] - yMutationDelta),
//                                        Math.min(nextY, vertex[1] + yMutationDelta));
//                            }
//                            individual.setVertex(i, j, vertex[0], yCoord);
//                        } else {    // left or right edge
//                            if (Math.max(previousX, vertex[0] - xMutationDelta) >= Math.min(nextX, vertex[0] + xMutationDelta)) {
//                                xCoord = Math.max(previousX, vertex[0] - xMutationDelta);
//                            } else {
//                                xCoord = random.nextInt(Math.max(previousX, vertex[0] - xMutationDelta),
//                                        Math.min(nextX, vertex[0] + xMutationDelta));
//                            }
//                            individual.setVertex(i, j, xCoord, vertex[1]);
//                        }
                    } else {
                        int xCoord, yCoord;
                        if (Math.max(previousX, vertex[0] - xMutationDelta) >= Math.min(nextX, vertex[0] + xMutationDelta)) {
                            xCoord = Math.max(previousX, vertex[0] - xMutationDelta);
                        } else {
                            xCoord = random.nextInt(Math.max(previousX, vertex[0] - xMutationDelta),
                                    Math.min(nextX, vertex[0] + xMutationDelta));
                        }
                        if (Math.max(previousY, vertex[1] - yMutationDelta) >= Math.min(nextY, vertex[1] + yMutationDelta)) {
                            yCoord = Math.max(previousY, vertex[1] - yMutationDelta);
                        } else {
                            yCoord = random.nextInt(Math.max(previousY, vertex[1] - yMutationDelta),
                                    Math.min(nextY, vertex[1] + yMutationDelta));
                        }
                        individual.setVertex(i, j, xCoord, yCoord);
                    }
                }
                previousX = individual.getVertex(i, j)[0];
                previousY = individual.getVertex(
                        Math.max(0, i - 1), Math.min(individual.getVertices()[0].length - 1, j + 1))[1];
            }
            previousX = 0;
            previousY = individual.getVertex(i, 0)[1];
        }
    }

    private void mutatePolygonColour(GridIndividual individual) {
        for (int i = 0; i < individual.getColours().length; ++i) {
            for (int j = 0; j < individual.getColours()[0].length; ++j) {
                if (shouldMutate()) {
                    int mutationDelta = (int) (255 * mutationExtent);
                    int[] polygonColour = individual.getPolygonColour(i, j);
                    int red = random.nextInt(Math.max(0, polygonColour[0] - mutationDelta),
                            Math.min(255, polygonColour[0] + mutationDelta));
                    int green = random.nextInt(Math.max(0, polygonColour[1] - mutationDelta),
                            Math.min(255, polygonColour[1] + mutationDelta));
                    int blue = random.nextInt(Math.max(0, polygonColour[2] - mutationDelta),
                            Math.min(255, polygonColour[2] + mutationDelta));
                    individual.setColour(i, j, red, green, blue);
                }
            }
        }
    }
}
