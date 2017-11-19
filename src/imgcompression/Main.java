package imgcompression;

import imgcompression.ea.EvolutionAlgorithmConfiguration;
import imgcompression.helper.GraphicHelper;
import imgcompression.impl.GridCompressor;
import imgcompression.impl.GridIndividual;
import imgcompression.impl.crossover.GridAvgCrossoverFunction;
import imgcompression.impl.initialPopulation.EqualGridPopulationGenerator;
import imgcompression.impl.selection.RouletteWheelSelection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
                .initialPopulationGenerator(new EqualGridPopulationGenerator(inputImage, 100))
                .selection(new RouletteWheelSelection<>())
                .crossover(new GridAvgCrossoverFunction())
                .mutation(o -> o)   // TODO implement mutation
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
