package imgcompression;

import imgcompression.ea.EvolutionAlgorithmConfiguration;
import imgcompression.helper.GraphicHelper;
import imgcompression.impl.GridCompressor;
import imgcompression.impl.GridIndividual;
import imgcompression.impl.crossover.GridAvgCrossoverFunction;
import imgcompression.impl.crossover.GridNPointCrossoverFunction;
import imgcompression.impl.initialPopulation.EqualGridPopulationGenerator;
import imgcompression.impl.mutation.GridMutationFunction;
import imgcompression.impl.selection.RouletteWheelSelection;
import imgcompression.impl.selection.SimpleSelection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static imgcompression.helper.GraphicHelper.generateRandomColourPart;

/**
 * @author Ondřej Kratochvíl
 */
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Please specify input and output file names");
            System.exit(1);
        }
        // read the image
        String inputFileName = args[0];
        String outputFileName = args[1];
        BufferedImage inputImage = ImageIO.read(new File(inputFileName));

        EvolutionAlgorithmConfiguration<GridIndividual> configuration = EvolutionAlgorithmConfiguration.<GridIndividual>builder()
                .initialPopulationGenerator(new EqualGridPopulationGenerator(inputImage, 2))
                .selection(new RouletteWheelSelection<>())
//                .selection(new SimpleSelection<>())
//                .crossover(new GridAvgCrossoverFunction())
                .crossover(new GridNPointCrossoverFunction())
                .mutation(new GridMutationFunction(0.05, 0.5))
//                .mutation(individual -> {
//                    if (ThreadLocalRandom.current().nextDouble() < 0.05) {
//                        int[][][] vertices = individual.getVertices();
//                        GridIndividual gridIndividual = new GridIndividual(individual.getInputImage(), vertices.length, vertices[0].length, individual.getFitnessFunction());
//                        int[][][] colours = individual.getColours();
//                        for (int i = 0; i < colours.length; ++i) {
//                            for (int j = 0; j < colours[0].length; ++j) {
//                                gridIndividual.setColour(i, j, generateRandomColourPart(), generateRandomColourPart(), generateRandomColourPart());
//                            }
//                        }
//                        return gridIndividual;
//                    }
//                    return individual;
//                })
                .build();
        GridCompressor compressor = new GridCompressor(inputImage, outputFileName, true, configuration);
        compressor.run();

//        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());
//        Graphics2D graphics = outputImage.createGraphics();
//        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
//        graphics.setRenderingHints(renderingHints);
//        Polygon polygon = new Polygon();
//        polygon.addPoint(90, 50);
//        polygon.addPoint(90, 80);
//        polygon.addPoint(60, 10);
//        polygon.addPoint(20, 10);
//        Color colour = new Color(GraphicHelper.convertToARGB(125, 201, 61));
//        graphics.setColor(colour);
//        graphics.fillPolygon(polygon);
//        ImageIO.write(outputImage, "jpeg", new File("out/test.jpeg"));
    }
}
