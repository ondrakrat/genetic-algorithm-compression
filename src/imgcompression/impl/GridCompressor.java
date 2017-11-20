package imgcompression.impl;

import imgcompression.ea.EvolutionAlgorithmConfiguration;
import imgcompression.ea.EvolutionAlgorithmExecutor;
import imgcompression.helper.GraphicHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static imgcompression.helper.GraphicHelper.convertToARGB;

/**
 * @author Ondřej Kratochvíl
 */
public class GridCompressor extends EvolutionAlgorithmExecutor<GridIndividual> {

    private final boolean debug;
    private final BufferedImage inputImage;
    private final String outputFileName;
    private int generation = 1;

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
        // TODO consider changing the EA functions to return streams instead of collections
        Collection<GridIndividual> population = createInitialPopulation();
        writeOutput(population);
        // TODO implement some actual terminating condition - add as EA functional interface
        while (generation < 1000) {
            // TODO rewrite to streams and execute in parallel
            List<GridIndividual> newPopulation = new ArrayList<>(population.size());
            for (int i = 0; i < population.size(); ++i) {
                GridIndividual parent1 = selection(population);
                GridIndividual parent2 = selection(population);
                GridIndividual child = crossover(parent1, parent2);
                newPopulation.add(mutation(child));
            }
            population = newPopulation;
            postGenerationAction(population);
        }
        writeOutput(population);
    }

    @Override
    protected void postGenerationAction(Collection<GridIndividual> newPopulation) {
        if (debug) {
            double averageFitness = newPopulation.stream()
                    .mapToDouble(GridIndividual::fitness)
                    .sum()
                    / newPopulation.size();
            System.out.printf("Generation: %d, average fitness: %f\n", generation, averageFitness);
            if (generation % 100 == 0) { // TODO adjust this value
                writeOutput(newPopulation);
            }
        }
        ++generation;
    }

    private void writeOutput(Collection<GridIndividual> population) {
        GridIndividual bestIndividual = population.stream()
                .sorted(Comparator.comparingDouble(GridIndividual::fitness).reversed())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Populations is empty"));
        int lastDotIndex = outputFileName.lastIndexOf('.');
        String fileName = String.format("%s_%d%s",
                outputFileName.substring(0, lastDotIndex), generation, outputFileName.substring(lastDotIndex));
        renderImage(bestIndividual, fileName);
    }

    private void renderImage(GridIndividual individual, String fileName) {
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
        try {
            ImageIO.write(outputImage, "jpeg", new File(fileName));
        } catch (IOException e) {
            System.err.println("Cannot render image");
            e.printStackTrace();
        }
    }
}
