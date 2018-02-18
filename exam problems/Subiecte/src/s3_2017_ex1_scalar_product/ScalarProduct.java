package s3_2017_ex1_scalar_product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Write a paralel program that computes the scalar product of 2 vectors.
 * The final summing should be done in a balanced tree
 */
public class ScalarProduct {

    public static final int N_THREADS = 4;

    public static ExecutorService service;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> vector1 = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1));
        List<Integer> vector2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

        System.out.println(vector1.size());
        if (vector1.size() != vector2.size()) {
            throw new RuntimeException("The vectors must have same size");
        }
        service = Executors.newFixedThreadPool(N_THREADS);
        System.out.println(vectorSum(0, vector1.size(), vector1, vector2, N_THREADS));
        service.shutdown();
    }

    private static int vectorSum(int start, int end, List<Integer> v1, List<Integer> v2, int nrThreads) throws
        ExecutionException,
        InterruptedException {
        if (nrThreads >= 2) {
            int mid = start + (end - start) / 2;
            Future<Integer> f1 = service.submit(() -> vectorSum(start, mid, v1, v2, nrThreads / 2));
            Future<Integer> f2 = service.submit(() -> vectorSum(mid, end, v1, v2, nrThreads - nrThreads / 2));
            return f1.get() + f2.get();
        } else {
            int s = 0;
            for (int i = start; i < end; i++) {
                s += (v1.get(i) * v2.get(i));
            }
            return s;
        }
    }
}


