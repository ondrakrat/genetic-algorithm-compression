package imgcompression.ea;

import imgcompression.ea.functions.*;
import lombok.Getter;

/**
 * Common configuration of various functions of genetic algorithms (selection, mutation, crossover, ...).
 *
 * @author Ondřej Kratochvíl
 */
@Getter
public class EvolutionAlgorithmConfiguration<I extends Individual> {

    private final Crossover<I> crossoverFunction;
    private final Mutation<I> mutationFunction;
    private final Selection<I> selectionFunction;
    private final Fitness<I> fitnessFunction;
    private final InitialPopulationGenerator<I> initialPopulationGeneratorFunction;

    public EvolutionAlgorithmConfiguration(Crossover<I> crossoverFunction,
                                           Mutation<I> mutationFunction,
                                           Selection<I> selectionFunction,
                                           Fitness<I> fitnessFunction,
                                           InitialPopulationGenerator<I> initialPopulationGeneratorFunction) {
        this.crossoverFunction = crossoverFunction;
        this.mutationFunction = mutationFunction;
        this.selectionFunction = selectionFunction;
        this.fitnessFunction = fitnessFunction;
        this.initialPopulationGeneratorFunction = initialPopulationGeneratorFunction;
    }

    public static <I extends Individual> Builder<I> builder() {
        return new Builder<>();
    }

    public static class Builder<I extends Individual> {

        private Crossover<I> crossoverFunction;
        private Mutation<I> mutationFunction;
        private Selection<I> selectionFunction;
        private Fitness<I> fitnessFunction;
        private InitialPopulationGenerator<I> initialPopulationGeneratorFunction;

        private Builder() {
            // hide constructor
        }

        public Builder<I> crossover(Crossover<I> crossoverFunction) {
            this.crossoverFunction = crossoverFunction;
            return this;
        }

        public Builder<I> mutation(Mutation<I> mutationFunction) {
            this.mutationFunction = mutationFunction;
            return this;
        }

        public Builder<I> selection(Selection<I> selectionFunction) {
            this.selectionFunction = selectionFunction;
            return this;
        }

        public Builder<I> fitness(Fitness<I> fitnessFunction) {
            this.fitnessFunction = fitnessFunction;
            return this;
        }

        public Builder<I> initialPopulationGenerator(InitialPopulationGenerator<I> initialPopulationGeneratorFunction) {
            this.initialPopulationGeneratorFunction = initialPopulationGeneratorFunction;
            return this;
        }

        public EvolutionAlgorithmConfiguration<I> build() {
            assert checkFunctions();
            return new EvolutionAlgorithmConfiguration<>(
                    crossoverFunction,
                    mutationFunction,
                    selectionFunction,
                    fitnessFunction,
                    initialPopulationGeneratorFunction);
        }

        /**
         * Checks if all functions are assigned and thus the {@link EvolutionAlgorithmConfiguration} can be built.
         *
         * @return {@code true} if the {@link EvolutionAlgorithmConfiguration} can be safely built
         */
        private boolean checkFunctions() {
            return crossoverFunction != null
                    && mutationFunction != null
                    && selectionFunction != null
                    && fitnessFunction != null
                    && initialPopulationGeneratorFunction != null;
        }
    }
}
