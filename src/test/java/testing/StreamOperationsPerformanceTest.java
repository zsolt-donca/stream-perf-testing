package testing;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Benchmark                                             (containerSize)   Mode  Cnt    Score   Error  Units
 * StreamOperationsPerformanceTest.testV1Parallel                1048576  thrpt  200  139.265 ± 2.225  ops/s
 * StreamOperationsPerformanceTest.testV1SingleThreaded          1048576  thrpt  200   46.603 ± 1.718  ops/s
 * StreamOperationsPerformanceTest.testV2                        1048576  thrpt  200  232.494 ± 2.906  ops/s
 *
 * Benchmark                                             (containerSize)   Mode  Cnt  Score   Error  Units
 * StreamOperationsPerformanceTest.testV1Parallel              104857600  thrpt   20  0.846 ± 0.059  ops/s
 * StreamOperationsPerformanceTest.testV1SingleThreaded        104857600  thrpt   20  0.420 ± 0.010  ops/s
 * StreamOperationsPerformanceTest.testV2                      104857600  thrpt   20  1.900 ± 0.107  ops/s
 */
@State(Scope.Benchmark)
@Fork(1)
public class StreamOperationsPerformanceTest {

    @Param({ "1048576" /*, "104857600"*/ })
    private int containerSize;

    private List<Integer> list;

    private Random random = new Random();

    @Setup
    public void setup() {
        list = new ArrayList<>(containerSize);
        int value = 0;
        for (int i = 0; i < containerSize; i++) {
            value += random.nextInt(5);
            list.add(value);
        }
    }

    @Benchmark
    public Object testV1SingleThreaded() {
        Stream<Integer> stream = list.stream();
        return StreamOperationsV1.isSorted(stream, Comparator.naturalOrder());
    }

    @Benchmark
    public Object testV1Parallel() {
        Stream<Integer> stream = list.parallelStream();
        return StreamOperationsV1.isSorted(stream, Comparator.naturalOrder());
    }

    @Benchmark
    public Object testV2() {
        Stream<Integer> stream = list.stream();
        return StreamOperationsV2.isSorted(stream, Comparator.naturalOrder());
    }
}
