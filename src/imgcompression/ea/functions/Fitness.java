package imgcompression.ea.functions;

import imgcompression.ea.Individual;

import java.util.function.ToDoubleFunction;

/**
 * Fitness function calculator.
 *
 * @author Ondřej Kratochvíl
 */
@FunctionalInterface
public interface Fitness<I extends Individual> extends ToDoubleFunction<I> {

    default void invalidateCache() {
        // to be implemented by caching algorithms
    }
}
