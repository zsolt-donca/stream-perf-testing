package testing;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.openjdk.jmh.annotations.Mode.AverageTime;

@State(Scope.Benchmark)
@Fork(value = 1, warmups = 0, jvmArgsPrepend = "-Xmx8g")
@Warmup(iterations = 2, batchSize = 1)
@Measurement(iterations = 3, batchSize = 1)
@BenchmarkMode(AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class RamPerfTest {

    @Param({ "1024", "1216", "1456", "1728", "2048", "2432", "2896", "3440", "4096", "4864", "5792", "6896", "8192", "9744", "11584", "13776", "16384", "19488",
            "23168", "27552", "32768", "38960", "46336", "55104", "65536", "77936", "92688", "110224", "131072", "155872", "185360", "220432", "262144", "311744",
            "370720", "440864", "524288", "623488", "741456", "881744", "1048576", "1246976", "1482912", "1600000", "1763488", "2097152", "2493952", "2965824",
            "3526976", "4194304", "4987904", "5931648", "7053952", "8388608", "9975792", "11863280", "14107904", "16000000", "16777216", "19951584", "23726560",
            "28215808", "33554432", "39903168", "47453136", "56431600", "67108864", "79806336", "94906272", "112863200", "134217728", "159612672", "160000000",
            "189812528", "225726416", "268435456", "319225360", "379625056", "451452832", "536870912", "638450704" })
    public int SIZE;

    private Entry first;

    @Setup
    public void setup() {
        int length = SIZE / 16;
        Entry[] entries = new Entry[length];
        for (int i = 0; i < entries.length; ++i) {
            entries[i] = new Entry();
        }

        Collections.shuffle(Arrays.asList(entries));

        Random random = new Random();
        for (int i = 0; i < entries.length - 1; ++i) {
            entries[i].next = entries[i + 1];
            entries[i].data = random.nextInt();
        }
        first = entries[0];
    }

    @Benchmark
    public int traverse() {
        Entry elem = first;
        while (true) {
            if (elem.next == null) {
                return elem.data;
            }
            elem = elem.next;
        }
    }
}

/*
# Run complete. Total time: 00:10:29

Benchmark                (SIZE)  Mode  Cnt        Score         Error  Units
RamPerfTest.traverse       1024  avgt    3        0.101 ±       0.017  us/op
RamPerfTest.traverse       1216  avgt    3        0.119 ±       0.025  us/op
RamPerfTest.traverse       1456  avgt    3        0.142 ±       0.027  us/op
RamPerfTest.traverse       1728  avgt    3        0.166 ±       0.036  us/op
RamPerfTest.traverse       2048  avgt    3        0.195 ±       0.008  us/op
RamPerfTest.traverse       2432  avgt    3        0.230 ±       0.068  us/op
RamPerfTest.traverse       2896  avgt    3        0.271 ±       0.042  us/op
RamPerfTest.traverse       3440  avgt    3        0.323 ±       0.077  us/op
RamPerfTest.traverse       4096  avgt    3        0.381 ±       0.002  us/op
RamPerfTest.traverse       4864  avgt    3        0.444 ±       0.135  us/op
RamPerfTest.traverse       5792  avgt    3        0.529 ±       0.031  us/op
RamPerfTest.traverse       6896  avgt    3        0.626 ±       0.048  us/op
RamPerfTest.traverse       8192  avgt    3        0.755 ±       0.104  us/op
RamPerfTest.traverse       9744  avgt    3        0.892 ±       0.090  us/op
RamPerfTest.traverse      11584  avgt    3        1.059 ±       0.126  us/op
RamPerfTest.traverse      13776  avgt    3        1.263 ±       0.271  us/op
RamPerfTest.traverse      16384  avgt    3        1.518 ±       0.293  us/op
RamPerfTest.traverse      19488  avgt    3        1.799 ±       0.195  us/op
RamPerfTest.traverse      23168  avgt    3        2.396 ±       0.538  us/op
RamPerfTest.traverse      27552  avgt    3        3.661 ±       0.547  us/op
RamPerfTest.traverse      32768  avgt    3        5.041 ±       0.389  us/op
RamPerfTest.traverse      38960  avgt    3        6.426 ±       0.923  us/op
RamPerfTest.traverse      46336  avgt    3        8.052 ±       1.363  us/op
RamPerfTest.traverse      55104  avgt    3       10.055 ±       1.834  us/op
RamPerfTest.traverse      65536  avgt    3       12.549 ±       3.844  us/op
RamPerfTest.traverse      77936  avgt    3       15.315 ±       1.765  us/op
RamPerfTest.traverse      92688  avgt    3       18.101 ±       4.304  us/op
RamPerfTest.traverse     110224  avgt    3       22.544 ±       2.030  us/op
RamPerfTest.traverse     131072  avgt    3       26.930 ±       5.620  us/op
RamPerfTest.traverse     155872  avgt    3       32.871 ±      11.083  us/op
RamPerfTest.traverse     185360  avgt    3       51.927 ±       4.888  us/op
RamPerfTest.traverse     220432  avgt    3       90.686 ±       7.573  us/op
RamPerfTest.traverse     262144  avgt    3      130.864 ±     117.777  us/op
RamPerfTest.traverse     311744  avgt    3      182.112 ±      32.696  us/op
RamPerfTest.traverse     370720  avgt    3      241.071 ±     213.737  us/op
RamPerfTest.traverse     440864  avgt    3      298.844 ±      36.048  us/op
RamPerfTest.traverse     524288  avgt    3      377.136 ±     128.595  us/op
RamPerfTest.traverse     623488  avgt    3      458.076 ±      50.604  us/op
RamPerfTest.traverse     741456  avgt    3      583.234 ±      27.431  us/op
RamPerfTest.traverse     881744  avgt    3      687.543 ±     242.788  us/op
RamPerfTest.traverse    1048576  avgt    3      869.264 ±     571.436  us/op
RamPerfTest.traverse    1246976  avgt    3     1025.905 ±     537.408  us/op
RamPerfTest.traverse    1482912  avgt    3     1283.639 ±    1073.889  us/op
RamPerfTest.traverse    1600000  avgt    3     1424.149 ±     299.882  us/op
RamPerfTest.traverse    1763488  avgt    3     1651.667 ±    1917.119  us/op
RamPerfTest.traverse    2097152  avgt    3     2017.269 ±    1421.858  us/op
RamPerfTest.traverse    2493952  avgt    3     3020.505 ±    4428.191  us/op
RamPerfTest.traverse    2965824  avgt    3     3787.372 ±    2208.260  us/op
RamPerfTest.traverse    3526976  avgt    3     6160.160 ±    3928.200  us/op
RamPerfTest.traverse    4194304  avgt    3     9722.417 ±    4685.432  us/op
RamPerfTest.traverse    4987904  avgt    3    14185.396 ±   15818.869  us/op
RamPerfTest.traverse    5931648  avgt    3    19482.133 ±    2700.571  us/op
RamPerfTest.traverse    7053952  avgt    3    24690.122 ±    8599.713  us/op
RamPerfTest.traverse    8388608  avgt    3    33720.773 ±   16204.976  us/op
RamPerfTest.traverse    9975792  avgt    3    43561.921 ±   22416.344  us/op
RamPerfTest.traverse   11863280  avgt    3    52600.592 ±   23236.987  us/op
RamPerfTest.traverse   14107904  avgt    3    66975.784 ±   20452.629  us/op
RamPerfTest.traverse   16000000  avgt    3    86693.136 ±   31174.290  us/op
RamPerfTest.traverse   16777216  avgt    3    91617.006 ±  112735.849  us/op
RamPerfTest.traverse   19951584  avgt    3   115876.677 ±   33324.139  us/op
RamPerfTest.traverse   23726560  avgt    3   148555.712 ±   30286.210  us/op
RamPerfTest.traverse   28215808  avgt    3   176840.270 ±  162346.225  us/op
RamPerfTest.traverse   33554432  avgt    3   217802.712 ±  128733.907  us/op
RamPerfTest.traverse   39903168  avgt    3   255173.124 ±   27143.026  us/op
RamPerfTest.traverse   47453136  avgt    3   320956.272 ±   41447.201  us/op
RamPerfTest.traverse   56431600  avgt    3   390992.441 ±  363325.873  us/op
RamPerfTest.traverse   67108864  avgt    3   448843.774 ±  104468.654  us/op
RamPerfTest.traverse   79806336  avgt    3   527686.201 ±   65682.311  us/op
RamPerfTest.traverse   94906272  avgt    3   656910.801 ±   45270.710  us/op
RamPerfTest.traverse  112863200  avgt    3   773243.452 ±   21824.962  us/op
RamPerfTest.traverse  134217728  avgt    3   908163.406 ±   84571.572  us/op
RamPerfTest.traverse  159612672  avgt    3  1171736.645 ±  343037.419  us/op
RamPerfTest.traverse  160000000  avgt    3  1136631.956 ± 1660932.248  us/op
RamPerfTest.traverse  189812528  avgt    3  1354315.980 ±  811096.090  us/op
RamPerfTest.traverse  225726416  avgt    3  1603015.365 ±  739134.663  us/op
RamPerfTest.traverse  268435456  avgt    3  1915998.590 ±   80172.891  us/op
RamPerfTest.traverse  319225360  avgt    3  2217548.191 ±  326249.876  us/op
RamPerfTest.traverse  379625056  avgt    3  2644313.477 ±  647639.410  us/op
RamPerfTest.traverse  451452832  avgt    3  3165858.882 ± 1650264.869  us/op
RamPerfTest.traverse  536870912  avgt    3  3767276.691 ±  118928.478  us/op
RamPerfTest.traverse  638450704  avgt    3  4514302.002 ±  644902.765  us/op

 */