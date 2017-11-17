package imgcompression;

import imgcompression.ea.EvolutionAlgorithmConfiguration;
import imgcompression.impl.GridCompressor;
import imgcompression.impl.GridIndividual;
import imgcompression.impl.crossover.GridAvgCrossoverFunction;
import imgcompression.impl.initialPopulation.EqualGridPopulationGenerator;
import imgcompression.impl.selection.SimpleSelection;

import javax.imageio.ImageIO;
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
                .selection(new SimpleSelection<>(0.1))
                .crossover(new GridAvgCrossoverFunction())
                .mutation(o -> o)   // TODO implement mutation
                .build();
        GridCompressor compressor = new GridCompressor(inputImage, outputFileName, true, configuration);
        compressor.run();
    }
}
