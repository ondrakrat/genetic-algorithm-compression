package imgcompression.ea.functions;

import imgcompression.ea.Individual;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Initial population generator function.
 *
 * @author Ondřej Kratochvíl
 */
@FunctionalInterface
public interface InitialPopulationGenerator<I extends Individual> extends Supplier<Collection<I>> {
}
