package imgcompression.ea;

import imgcompression.ea.functions.Crossover;
import imgcompression.ea.functions.Mutation;
import imgcompression.ea.functions.Selection;
import lombok.Getter;

/**
 * Common configuration of various functions of genetic algorithms (selection, mutation, crossover, ...).
 *
 * @author Ondřej Kratochvíl
 */
@Getter
public class EvolutionAlgorithmConfiguration<I extends Individual> {

    private Crossover<I> crossoverFunction;
    private Mutation<I> mutationFunction;
    private Selection<I> selectionFunction;

    public EvolutionAlgorithmConfiguration(Crossover<I> crossoverFunction,
                                           Mutation<I> mutationFunction,
                                           Selection<I> selectionFunction) {
        this.crossoverFunction = crossoverFunction;
        this.mutationFunction = mutationFunction;
        this.selectionFunction = selectionFunction;
    }

    public static <I extends Individual> Builder<I> builder() {
        return new Builder<>();
    }

    public static class Builder<I extends Individual> {

        private Crossover<I> crossoverFunction;
        private Mutation<I> mutationFunction;
        private Selection<I> selectionFunction;

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

        public EvolutionAlgorithmConfiguration<I> build() {
            assert crossoverFunction != null && mutationFunction != null && selectionFunction != null;
            return new EvolutionAlgorithmConfiguration<>(
                    crossoverFunction,
                    mutationFunction,
                    selectionFunction
            );
        }
    }
}
