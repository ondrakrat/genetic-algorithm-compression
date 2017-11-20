package imgcompression.impl.selection;

import imgcompression.ea.Individual;
import imgcompression.ea.functions.Selection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ondřej Kratochvíl
 */
public class SimpleSelection<I extends Individual> implements Selection<I> {

    private Set<I> alreadySelected = new HashSet<>();

    @Override
    public I apply(Collection<I> individuals) {
        List<I> sorted = individuals.stream()
                .sorted(Comparator.comparingDouble(I::fitness).reversed())
                .collect(Collectors.toList());
        for (I alpha : sorted) {
            if (alreadySelected.contains(alpha)) {
                continue;
            }
            alreadySelected.add(alpha);
            return alpha;
        }
        System.out.println("No more unique individuals can be selected, looping again");
        alreadySelected.clear();
        return apply(individuals);
    }
}
