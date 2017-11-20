package imgcompression.impl.fitness;

import imgcompression.ea.functions.Fitness;
import imgcompression.helper.GraphicHelper;
import imgcompression.impl.GridIndividual;

import java.awt.*;
import java.awt.image.BufferedImage;

import static imgcompression.helper.GraphicHelper.convertToARGB;
import static imgcompression.helper.GraphicHelper.pixelDiff;

/**
 * @author Ondřej Kratochvíl
 */
public class GridFitnessFunction implements Fitness<GridIndividual> {

    private final BufferedImage inputImage;
    private Double cachedFitness = null;

    public GridFitnessFunction(BufferedImage inputImage) {
        this.inputImage = inputImage;
    }

    @Override
    public double applyAsDouble(GridIndividual value) {
        if (cachedFitness != null) {
            return cachedFitness;
        }

        double fitness = 0;
        BufferedImage renderedImage = renderImage(value);
        for (int j = 0; j < renderedImage.getHeight(); ++j) {
            for (int i = 0; i < renderedImage.getWidth(); ++i) {
                int inputPixel = inputImage.getRGB(i, j);
                int individualPixel = renderedImage.getRGB(i, j);
                double pixelDiff = pixelDiff(inputPixel, individualPixel);
                fitness += (pixelDiff * -1);    // so that lower diff results in better (greater) fitness
            }
        }
//        fitness = fitness / (renderedImage.getHeight() * renderedImage.getWidth());

        cachedFitness = fitness;
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

    private BufferedImage renderImage(GridIndividual individual) {
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());
        Graphics2D graphics = outputImage.createGraphics();
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics.setRenderingHints(renderingHints);
        for (int i = 1; i < individual.getVertices().length; ++i) {
            for (int j = 1; j < individual.getVertices()[0].length; ++j) {
                int[] bottomRight = individual.getVertex(i, j);
                int[] bottomLeft = individual.getVertex(i, j - 1);
                int[] upperRight = individual.getVertex(i - 1, j);
                int[] upperLeft = individual.getVertex(i - 1, j - 1);
                Polygon polygon = new Polygon();
                polygon.addPoint(bottomRight[0], bottomRight[1]);
                polygon.addPoint(bottomLeft[0], bottomLeft[1]);
                polygon.addPoint(upperLeft[0], upperLeft[1]);
                polygon.addPoint(upperRight[0], upperRight[1]);

                int[] polygonColour = individual.getPolygonColour(i - 1, j - 1);
                Color colour = new Color(GraphicHelper.convertToARGB(polygonColour[0], polygonColour[1], polygonColour[2]));
                graphics.setColor(colour);
                graphics.fillPolygon(polygon);
            }
        }
        return outputImage;
    }

    @Override
    public void invalidateCache() {
        cachedFitness = null;
    }
}
