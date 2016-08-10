package testing;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamOperationsV1 {

    public static <T> boolean isSorted(Stream<T> stream, Comparator<T> comparator) {
        Optional<SortedMinMax<T>> sortedMinMaxOption = stream
                .map(value -> new SortedMinMax<>(value, comparator))
                .reduce(SortedMinMax::merge);

        return sortedMinMaxOption
                .map(sortedMinMax -> sortedMinMax.sorted)
                .orElse(true);
    }

    private static class SortedMinMax<T> {
        private final boolean sorted;
        private final T min, max;
        private final Comparator<T> comparator;

        public SortedMinMax(T value, Comparator<T> comparator) {
            this.comparator = comparator;
            this.sorted = true;
            this.min = value;
            this.max = value;
        }

        public SortedMinMax(boolean sorted, T min, T max, Comparator<T> comparator) {
            this.sorted = sorted;
            this.min = min;
            this.max = max;
            this.comparator = comparator;
        }

        public SortedMinMax merge(SortedMinMax<T> that) {
            boolean sorted = this.sorted && that.sorted && lessThanOrEqual(this.max, that.min);

            T min = min(this.min, that.min);
            T max = max(this.max, that.max);

            return new SortedMinMax<>(sorted, min, max, comparator);
        }

        private boolean lessThanOrEqual(T a, T b) {
            return comparator.compare(a, b) <= 0;
        }

        private T min(T a, T b) {
            return comparator.compare(a, b) <= 0 ? a : b;
        }

        private T max(T a, T b) {
            return comparator.compare(a, b) >= 0 ? a : b;
        }
    }

}
