package imgcompression.impl.fitness;

import imgcompression.ea.functions.Fitness;
import imgcompression.impl.GridIndividual;

import java.awt.image.BufferedImage;

import static imgcompression.helper.GraphicHelper.convertToARGB;

/**
 * @author Ondřej Kratochvíl
 */
public class GridFitnessFunction implements Fitness<GridIndividual> {

    private final BufferedImage inputImage;

    public GridFitnessFunction(BufferedImage inputImage) {
        this.inputImage = inputImage;
    }

    @Override
    public double applyAsDouble(GridIndividual value) {
        double fitness = 0;
        for (int i = 1; i < value.getVertices().length - 1; ++i) {
            for (int j = 1; j < value.getVertices()[0].length - 1; ++j) {
                // TODO ensure that indices are started from the same edges (image x grid)
                fitness += diffForVertex(value.getVertex(i, j),
                        value.getPolygonColour(i, j), value.getVertex(i - 1, j - 1));
            }
        }
        return fitness;
    }

    private double diffForVertex(int[] vertex, int[] colour, int[] previousVertex) {
        double fitness = 0;
        for (int i = previousVertex[0]; i < vertex[0]; ++i) {
            for (int j = previousVertex[1]; j < vertex[1]; ++j) {
                int individualPixel = convertToARGB(colour[0], colour[1], colour[2]);
                int inputPixel = inputImage.getRGB(vertex[0], vertex[1]);
                // TODO is this correct?
                fitness += (individualPixel > inputPixel
                        ? (individualPixel / (double) inputPixel)
                        : (inputPixel / (double) individualPixel));
            }
        }
        return fitness;
    }
}
