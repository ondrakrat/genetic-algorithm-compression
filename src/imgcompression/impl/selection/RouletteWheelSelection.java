package imgcompression.impl.selection;

import imgcompression.ea.Individual;
import imgcompression.ea.functions.Selection;

import java.util.*;

/**
 * @author Ondřej Kratochvíl
 */
public class RouletteWheelSelection<I extends Individual> implements Selection<I> {

    private static final Random RANDOM = new Random();

    @Override
    public I apply(Collection<I> population) {
        double totalFitness = population.stream()
                .mapToDouble(Individual::fitness)
                .sum();
        double random = RANDOM.nextDouble();
        double current = 0;
        for (I individual : population) {
            if (current + (individual.fitness() / totalFitness) < random) {
                current += individual.fitness() / totalFitness;
            } else {
                return individual;
            }
        }
        return null;
    }
}
