package imgcompression.impl;

import imgcompression.ea.EvolutionAlgorithmConfiguration;
import imgcompression.ea.EvolutionAlgorithmExecutor;

import java.awt.image.BufferedImage;
import java.util.Collection;

/**
 * @author Ondřej Kratochvíl
 */
public class GridCompressor extends EvolutionAlgorithmExecutor<GridIndividual> {

    private final boolean debug;
    private final BufferedImage inputImage;
    private final String outputFileName;

    public GridCompressor(BufferedImage inputImage,
                          String outputFileName,
                          EvolutionAlgorithmConfiguration<GridIndividual> configuration) {
        super(configuration);
        this.debug = false;
        this.inputImage = inputImage;
        this.outputFileName = outputFileName;
    }

    public GridCompressor(BufferedImage inputImage,
                          String outputFileName,
                          boolean debug,
                          EvolutionAlgorithmConfiguration<GridIndividual> configuration) {
        super(configuration);
        this.debug = debug;
        this.inputImage = inputImage;
        this.outputFileName = outputFileName;
    }

    @Override
    public void run() {
        // TODO implement

    }

    @Override
    public Collection<GridIndividual> createInitialPopulation() {
        // TODO implement
        return null;
    }
}
