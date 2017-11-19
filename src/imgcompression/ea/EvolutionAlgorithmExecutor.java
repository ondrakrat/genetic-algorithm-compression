package imgcompression.ea;

import imgcompression.impl.GridIndividual;

import java.util.Collection;

/**
 * Common parent class for Genetic algorithm executors.
 *
 * @author Ondřej Kratochvíl
 */
public abstract class EvolutionAlgorithmExecutor<I extends Individual> {

    private final EvolutionAlgorithmConfiguration<I> configuration;

    public EvolutionAlgorithmExecutor(EvolutionAlgorithmConfiguration<I> configuration) {
        this.configuration = configuration;
    }

    public abstract void run();

    /**
     * Perform post generation actions, such as logging, writing intermediate output files etc.
     */
    protected void postGenerationAction(Collection<GridIndividual> newPopulation) {
        // do nothing by default
    }

    protected Collection<I> createInitialPopulation() {
        return configuration.getInitialPopulationGeneratorFunction().get();
    }

    protected Collection<I> selection(Collection<I> generation) {
        return configuration.getSelectionFunction().apply(generation);
    }

    protected I crossover(I i1, I i2) {
        return configuration.getCrossoverFunction().apply(i1, i2);
    }

    protected I mutation(I individual) {
        return configuration.getMutationFunction().apply(individual);
    }
}
