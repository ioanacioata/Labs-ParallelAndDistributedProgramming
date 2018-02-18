package s1_2018_ex1_permutation_laura;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimplePermutation {

    private static final Set<String> permutationSet = new HashSet<>();
    private static Integer conditionSatisfied = 0;

    public static void main(String[] args) throws InterruptedException {
        String s = "1213";
        int threadNumber = 3;

        // perm is the total number of permutations, that is the length of the string factorial
        int perm = 1;
        int digits = s.length();

        for (int i = 1; i <= digits; i++) {
            perm *= i;
        }

        int actionsPerThread = perm / threadNumber;
        int remainder = perm % threadNumber;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);

        int start = 0, end = 0;
        for (int j = 0; j < threadNumber; j++) {
            end += actionsPerThread;
            if (remainder > 0) {
                end += 1;
                remainder--;
            }
            int finalEnd = end;
            int finalStart = start;
//            System.out.println("final start: "+ finalStart + " final end: " + finalEnd);
            int finalPerm = perm;
            executorService.execute(() -> permute(s, finalEnd, digits, finalStart, finalPerm));
            start = end;
        }

        executorService.shutdown();
        executorService.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("Permutations: " + perm);
        System.out.println("Condition: " + conditionSatisfied);
    }

    private static void permute(String s, int stop, int digits, int start, int perm) {
        for (int a = start; a < stop; a++) {

            StringBuilder avail = new StringBuilder(s);
            StringBuilder result = new StringBuilder();
            for (int b = digits, div = perm; b > 0; b--) {
                div /= b;
                int index = (a / div) % b;
                result.append(avail.charAt(index));
//                try {
//                    avail.delete(index, 1);
//                }
//                catch (StringIndexOutOfBoundsException e){
//                }
                avail.setCharAt(index, avail.charAt(b - 1)); // non-lexigraphic but fast
            }
            synchronized (permutationSet) {
                if (!permutationSet.contains(result.toString())) {
                    permutationSet.add(result.toString());

                    synchronized (conditionSatisfied) {
                        if (prev(result.toString())) {
                            conditionSatisfied++;
                        }
                    }
                    System.out.println(result);
                }
            }
        }
    }

    private static boolean prev(String s) {
        return true;
    }
}