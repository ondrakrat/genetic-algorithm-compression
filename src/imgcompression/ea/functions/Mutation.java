package imgcompression.ea.functions;

import imgcompression.ea.Individual;

import java.util.function.Function;

/**
 * Mutation operator.
 *
 * @author Ondřej Kratochvíl
 */
@FunctionalInterface
public interface Mutation<I extends Individual> extends Function<I, I> {
}
