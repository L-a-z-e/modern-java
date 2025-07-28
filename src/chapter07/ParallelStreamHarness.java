package chapter07;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

public class ParallelStreamHarness {

    public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    public static void main(String[] args) {
        System.out.println("Iterative Sum done in: " + measurePerf(ParallelStream::iterativeSum, 10_000_000L) + " msecs");
        System.out.println("Sequential Sum done in: " + measurePerf(ParallelStream::sequentialSum, 10_000_000L) + " msecs");
        System.out.println("Parallel forkJoinSum done in: " + measurePerf(ParallelStream::parallelSum, 10_000_000L) + " msecs" );
        System.out.println("Range forkJoinSum done in: " + measurePerf(ParallelStream::rangedSum, 10_000_000L) + " msecs");
        System.out.println("Parallel range forkJoinSum done in: " + measurePerf(ParallelStream::parallelRangedSum, 10_000_000L) + " msecs" );
        System.out.println("ForkJoin sum done in: " + measurePerf(ForkJoinSumCalculator::forkJoinSum, 10_000_000L) + " msecs" );
        System.out.println("SideEffect sum done in: " + measurePerf(ParallelStream::sideEffectSum, 10_000_000L) + " msecs" );
        System.out.println("SideEffect parallel sum done in: " + measurePerf(ParallelStream::sideEffectParallelSum, 10_000_000L) + " msecs" );
    }

    private static <T,R> long measurePerf(Function<T, R> f, T input) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            R result = f.apply(input);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + result);
            if (duration < fastest)
                fastest = duration;
        }

        return fastest;
    }
}
