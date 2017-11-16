package imgcompression.ea;

/**
 * Common interface for individuals from a generation in genetic programming.
 *
 * @author Ondřej Kratochvíl
 */
public interface Individual {

    double fitness();

    /**
     * Compare this individual with the {@code other}.
     *
     * @param other other individual to be compared with this one
     * @return 0 if both of the individuals have the same fitness, positive number if <strong>this</strong> individual
     * has greater fitness, and negative number if the <strong>other</strong> individual has greater fitness. The
     * greater the fitness difference, the greater the absolute value of the output.
     */
    default double compareFitness(Individual other) {
        return fitness() - other.fitness();
    }
}
