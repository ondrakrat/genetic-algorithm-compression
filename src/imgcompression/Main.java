package imgcompression;

import imgcompression.ea.EvolutionAlgorithmConfiguration;
import imgcompression.impl.GridCompressor;
import imgcompression.impl.GridIndividual;

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
        // TODO implement EA methods
        EvolutionAlgorithmConfiguration<GridIndividual> configuration = EvolutionAlgorithmConfiguration.<GridIndividual>builder()
                .selection(collection -> null)
                .crossover((o, o2) -> null)
                .mutation(o -> null)
                .build();
        GridCompressor compressor = new GridCompressor(inputImage, outputFileName, true, configuration);
    }
}
