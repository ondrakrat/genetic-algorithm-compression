package imgcompression.ea.functions;

import imgcompression.ea.Individual;

import java.util.function.BiFunction;

/**
 * Crossover operator.
 *
 * @author Ondřej Kratochvíl
 */
@FunctionalInterface
public interface Crossover<I extends Individual> extends BiFunction<I, I, I> {
}
