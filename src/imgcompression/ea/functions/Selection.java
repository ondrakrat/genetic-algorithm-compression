package imgcompression.ea.functions;

import imgcompression.ea.Individual;

import java.util.Collection;
import java.util.function.Function;

/**
 * Selection operator.
 *
 * @author Ondřej Kratochvíl
 */
@FunctionalInterface
public interface Selection<I extends Individual> extends Function<Collection<I>, Collection<I>> {
    // TODO return only a single individual, cache fitness
}
