package testing;

import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

public class StreamOperationsV2 {

    public static <T> boolean isSorted(Stream<T> stream, Comparator<T> comparator) {
        Iterator<T> iterator = stream.iterator();
        if (iterator.hasNext()) {
            T previous = iterator.next();
            while (iterator.hasNext()) {
                T current = iterator.next();
                if (comparator.compare(previous, current) > 0) {
                    return false;
                }
                previous = current;
            }
        }
        return true;
    }
}
