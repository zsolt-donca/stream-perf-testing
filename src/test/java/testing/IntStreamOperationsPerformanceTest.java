package testing;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Random;
import java.util.stream.IntStream;


/**
 * Benchmark                                                (containerSize)   Mode  Cnt    Score    Error  Units
 * IntStreamOperationsPerformanceTest.testV1Parallel                1048576  thrpt   20  129.754 ±  3.953  ops/s
 * IntStreamOperationsPerformanceTest.testV1SingleThreaded          1048576  thrpt   20   71.950 ±  1.425  ops/s
 * IntStreamOperationsPerformanceTest.testV2                        1048576  thrpt   20  994.892 ± 26.811  ops/s
 */
@State(Scope.Benchmark)
@Fork(1)
public class IntStreamOperationsPerformanceTest {

    @Param({ "1048576" })
    private int containerSize;

    private int[] array;

    private Random random = new Random();

    @Setup
    public void setup() {
        array = new int[containerSize];
        int value = 0;
        for (int i = 0; i < array.length; i++) {
            value += random.nextInt(5);
            array[i] = value;
        }
    }

    @Benchmark
    public Object testV1SingleThreaded() {
        IntStream intStream = IntStream.of(this.array);
        return IntStreamOperationsV1.isSorted(intStream);
    }

    @Benchmark
    public Object testV1Parallel() {
        IntStream intStream = IntStream.of(this.array);
        return IntStreamOperationsV1.isSorted(intStream.parallel());
    }

    @Benchmark
    public Object testV2() {
        IntStream intStream = IntStream.of(this.array);
        return IntStreamOperationsV2.isSorted(intStream);
    }
}
