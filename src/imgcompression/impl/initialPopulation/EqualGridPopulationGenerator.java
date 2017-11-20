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

    private static final int REDUCTION_FACTOR = 50;

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
                yDimension,
                xDimension, new GridFitnessFunction(inputImage));
        for (int i = 0; i <= yDimension; ++i) {
            for (int j = 0; j <= xDimension; ++j) {
                int xCoord = j == 0 ? 0 : (j == xDimension ? inputImage.getWidth() : ((inputImage.getWidth() / xDimension) * j));
                int yCoord = i == 0 ? 0 : (i == yDimension ? inputImage.getHeight() : ((inputImage.getHeight() / yDimension) * i));
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
