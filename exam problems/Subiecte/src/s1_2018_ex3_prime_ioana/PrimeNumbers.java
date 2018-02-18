package s1_2018_ex3_prime_ioana;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrimeNumbers {
    public static void main(String[] args) {
        int n = 100;
        int nrThreads = 4;
        int root = (int) Math.sqrt(n);


        //find the prime numbers until sqrt(n)
        List<Integer> primeList = new ArrayList<>();
        if (2 <= root) {
            primeList.add(2);
        }
        for (int i = 3; i <= root; i = i + 2) {
            if (isPrime(i)) {
                primeList.add(i);
            }
        }

//        System.out.println("Final List: " + primeList.toString());

        //start work on threads

        long startTime = System.currentTimeMillis();

        int nrPerThread = (n - root + 1) / nrThreads;
        System.out.println(nrPerThread);

        //each thread tries to find the prime numbers from the interval : [start, end)
        int start = root + 1;
        int end = start + nrPerThread;
        ExecutorService executorService = Executors.newFixedThreadPool(nrThreads);
        List<Integer> finalList = new ArrayList<>();
        finalList.addAll(primeList);

        for (int i = 0; i < nrThreads; i++) {
            //the function need constants
            int finalStart = start;
            int finalEnd = end;
            Future<List<Integer>> future = executorService.submit(() -> getPrimeList(finalStart, finalEnd, primeList));
            //set the next interval
            start = end;
            end = end + nrPerThread;
            try {
                List<Integer> result = future.get();
                if (!result.isEmpty()) {
                    finalList.addAll(result); //if any prime numbers are found, they are added to the final list
                }
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("EXCEPTIOON1 ... ");
                e.printStackTrace();
            }
        }

        //the remaining numbers, if it's the case
        if (end < n) {
            int finalStart = end;
            Future<List<Integer>> future = executorService.submit(() -> getPrimeList(finalStart, n + 1, primeList));
            try {
                List<Integer> result = future.get();
                if (!result.isEmpty()) {
                    finalList.addAll(result);
                }
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("EXCEPTIOON2  ... ");
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Time Elapsed: " + (endTime - startTime));

        executorService.shutdown();

        System.out.println("Final List: " + finalList.toString());
    }

    /**
     * @param start     - first element verified
     * @param end       - end-1 is the last element verified (if prime)
     * @param primeList - the list calculated by the main thread
     * @return the list of prime numbers from the interval [start,end)
     */
    private static List<Integer> getPrimeList(int start, int end, List<Integer> primeList) {
        List<Integer> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (isPrimeWithList(i, primeList)) {
                result.add(i);
            }
        }
        System.out.println("start1: " + start + "  end1: " + end);

        return result;
    }

    /**
     * @param nr
     * @param primeList - the list of possible divisors
     * @return true if the number is Prime, false otherwise
     */
    private static boolean isPrimeWithList(int nr, List<Integer> primeList) {
        for (Integer d : primeList) {
            if (nr % d == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Classic implementation to verify if a number is prime
     *
     * @param nr
     * @return
     */
    private static boolean isPrime(int nr) {
        double root = Math.sqrt(nr);
        if (nr % 2 == 0) {
            return false;
        }
        for (int d = 3; d <= root; d = d + 2) {
            if (nr % d == 0) {
                return false;
            }
        }
        return true;
    }
}
