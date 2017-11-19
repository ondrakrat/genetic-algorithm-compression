package imgcompression.impl.initialPopulation;

import imgcompression.ea.functions.InitialPopulationGenerator;
import imgcompression.impl.GridIndividual;
import imgcompression.impl.fitness.GridFitnessFunction;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static imgcompression.helper.GraphicHelper.generateRandomColourPart;

/**
 * Generates the initial population of given size, the {@link imgcompression.ea.Individual}s have equally distributed
 * grid (squares/rectangles) of random colours.
 *
 * @author Ondřej Kratochvíl
 */
public class EqualGridPopulationGenerator implements InitialPopulationGenerator<GridIndividual> {

    private static final int REDUCTION_FACTOR = 10;

    private final BufferedImage inputImage;
    private final int populationSize;

    public EqualGridPopulationGenerator(BufferedImage inputImage, int populationSize) {
        this.inputImage = inputImage;
        this.populationSize = populationSize;
    }

    @Override
    public Collection<GridIndividual> get() {
        return IntStream.range(0, populationSize)
                .mapToObj(value -> createIndividual())
                .collect(Collectors.toSet());
    }

    private GridIndividual createIndividual() {
        int xDimension = inputImage.getWidth() / REDUCTION_FACTOR;
        int yDimension = inputImage.getHeight() / REDUCTION_FACTOR;
        GridIndividual individual = new GridIndividual(
                inputImage,
                xDimension,
                yDimension, new GridFitnessFunction(inputImage));
        for (int i = 0; i <= xDimension; ++i) {
            for (int j = 0; j <= yDimension; ++j) {
                // TODO handle edges
                int xCoord = i == 0 ? 0 : (i == xDimension ? inputImage.getWidth() : i * xDimension);
                // TODO j == 0 -> top, or bottom?
                int yCoord = j == 0 ? 0 : (j == yDimension ? inputImage.getHeight() : j * yDimension);
                individual.setVertex(i, j, xCoord, yCoord);
                if (i < xDimension && j < yDimension) {
                    individual.setColour(i, j,
                            generateRandomColourPart(), generateRandomColourPart(), generateRandomColourPart());
                }
            }
        }
        return individual;
    }
}
