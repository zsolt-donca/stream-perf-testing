package testing;

import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

public class IntStreamOperationsV2 {
    public static boolean isSorted(IntStream intStream) {
        PrimitiveIterator.OfInt iterator = intStream.iterator();
        if (iterator.hasNext()) {
            int previous = iterator.nextInt();
            while (iterator.hasNext()) {
                int current = iterator.nextInt();
                if (previous > current) {
                    return false;
                }
                previous = current;
            }
        }
        return true;
    }
}
