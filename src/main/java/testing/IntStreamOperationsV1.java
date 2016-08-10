package testing;

import java.util.Optional;
import java.util.stream.IntStream;

public class IntStreamOperationsV1 {

    public static boolean isSorted(IntStream stream) {
        Optional<SortedMinMax> sortedMinMaxOption = stream.mapToObj(SortedMinMax::new).reduce(SortedMinMax::merge);
        return sortedMinMaxOption.map(sortedMinMax -> sortedMinMax.sorted)
                .orElse(true);
    }

    private static class SortedMinMax {
        private final boolean sorted;
        private int min, max;

        public SortedMinMax(int i) {
            this.sorted = true;
            this.min = i;
            this.max = i;
        }

        public SortedMinMax(boolean sorted, int min, int max) {
            this.sorted = sorted;
            this.min = min;
            this.max = max;
        }

        public SortedMinMax merge(SortedMinMax that) {
            boolean sorted = this.sorted && that.sorted && this.max <= that.min;
            int min = Math.min(this.min, that.min);
            int max = Math.max(this.max, that.max);

            return new SortedMinMax(sorted, min, max);
        }
    }

}
