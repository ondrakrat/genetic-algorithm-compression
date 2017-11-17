package imgcompression.impl.selection;

import imgcompression.ea.Individual;
import imgcompression.ea.functions.Selection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ondřej Kratochvíl
 */
public class SimpleSelection<I extends Individual> implements Selection<I> {

    private final double selectedCount; // fraction of the generation to be selected

    public SimpleSelection(double selectedCount) {
        assert selectedCount > 0 && selectedCount <= 1;
        this.selectedCount = selectedCount;
    }

    @Override
    public Collection<I> apply(Collection<I> individuals) {
        List<I> sorted = individuals.stream()
                .sorted(Comparator.comparingDouble(I::fitness))
                .collect(Collectors.toList());
        return sorted.subList(0, (int) (individuals.size() * selectedCount));
    }
}
