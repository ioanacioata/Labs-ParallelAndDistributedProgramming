package s2_2017_ex1_hamiltonian;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This uses a recursive function that returns  boolean if a path is found
 *
 */
public class Hamiltonian {
    public static final int THREAD_NUMBER = 4;
    //no Hamiltonian path
//    public static Integer graph[][] = {
//        {0, 1, 1, 0, 0},
//        {1, 0, 0, 0, 1},
//        {1, 0, 0, 0, 0},
//        {0, 1, 0, 0, 0},
//        {0, 1, 0, 0, 0}
//    };

    //has at least one Hamiltonian path
    public static Integer graph[][] = {
        {0, 1, 1, 1, 0},
        {1, 0, 0, 1, 1},
        {1, 0, 0, 0, 1},
        {1, 1, 0, 0, 1},
        {0, 1, 1, 1, 0}
    };

    public static ExecutorService executorService;
//    public static AtomicBoolean found = new AtomicBoolean(false);
    public static Integer size = graph[0].length; //nr of vertices

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String s = "01342";// Hamiltonian
//        String s = "01234";//not Hamiltonian

        int length = s.length();

        List<String> digits = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            digits.add(String.valueOf(s.charAt(i)));
        }

        System.out.println(digits.toString());

        executorService = Executors.newFixedThreadPool(THREAD_NUMBER);
        int nrThreads = length;
        List<String> solution = new ArrayList<>();
        Future<Boolean> result = executorService.submit(() -> search(solution, digits, nrThreads));
//        System.out.println("CAN FIND HAMILTONIAN: atomic" + found.get());
        System.out.println("CAN FIND HAMILTONIAN: " + result.get());

        executorService.shutdown();

        System.out.println(isHamiltonian(s));
    }


    //this function could be void; the result is also in the atomic boolean found
    private static Boolean search(List<String> partialSolution, List<String> digits, int nrThreads) {
        //verify if we already found a Hamiltonian path
//        if (found.get()) {
//            return true;
//        }
        //This is the case where we completed a permutation
        if (partialSolution.size() == size) {
            StringBuilder s = new StringBuilder();
            for (String aPartialSolution : partialSolution) {
                s.append(String.valueOf(aPartialSolution));
            }
            boolean hamiltonian = isHamiltonian(s.toString());
//            if (hamiltonian) {
//                //if a Hamiltonian path is found we modify the atomic boolean
//                System.out.println("IS HAMILTONIAM PPLMMMMMMMM" + partialSolution.toString());
//                found = new AtomicBoolean(true);
//            }
            return hamiltonian;
        }

        //split the work
        if (nrThreads > 1) {
            List<Future<Boolean>> futures = new ArrayList<>();
            for (int i = 0; i < digits.size(); i++) {
                //add one digit to the partial solution
                List<String> partSum2 = new ArrayList<>(partialSolution);
                partSum2.add(digits.get(i));

                //delete the digit that was added to the partial solution
                List<String> digits2 = new ArrayList<>(digits);
                digits2.remove(digits.get(i));

                System.out.println("partialSolution2: " + partSum2.toString());
                System.out.println("digits2: " + digits2.toString());

                Future<Boolean> future = executorService.submit(() -> search(partSum2, digits2, nrThreads / digits
                    .size()));
                futures.add(future);
            }
            try {
                //if ANY path is found we return true
                for (Future<Boolean> f : futures) {
                    if (f.get()) {
                        return true;
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            Boolean found = false;
            //add a new digit to the permutation
            for (int i = 0; i < digits.size(); i++) {
                //update partial solution
                List<String> partSum2 = new ArrayList<>(partialSolution);
                partSum2.add(digits.get(i));
                //delete the digit passed
                List<String> digits2 = new ArrayList<>(digits);
                digits2.remove(digits.get(i));

//                System.out.println("partialSolution2: " + partSum2.toString());
//                System.out.println("digits2: " + digits2.toString());

                //recursive call --not necessary
                found = found || search(partSum2, digits2, nrThreads);
            }
            return found;
        }
    }

    public static boolean isHamiltonian(String permutation) {
        for (int i = 0; i < size - 1; i++) {
            Integer vertex1 = Integer.valueOf(permutation.charAt(i) + "");
            Integer vertex2 = Integer.valueOf(permutation.charAt(i + 1) + "");
            if (!hasEdge(vertex1, vertex2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasEdge(int v1, int v2) {
        return (graph[v1][v2] == 1 && v1 != v2);
    }
}
