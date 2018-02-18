package s2_2017_ex1_hamiltonian;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This uses a void recursive function and the result is
 * hold in a atomic boolean, declared globally.
 * <p>
 * It stops earlier ( at the first found path)
 */
public class Hamiltonian2 {
    public static Integer graph[][] = {
        {0, 1, 1, 0, 0},
        {1, 0, 0, 0, 1},
        {1, 0, 0, 0, 0},
        {0, 1, 0, 0, 0},
        {0, 1, 0, 0, 0}
    };
//
//    public static Integer graph[][] = {
//        {0, 1, 1, 1, 0},
//        {1, 0, 0, 1, 1},
//        {1, 0, 0, 0, 1},
//        {1, 1, 0, 0, 1},
//        {0, 1, 1, 1, 0}
//    };

    public static ExecutorService service;
    public static AtomicBoolean found = new AtomicBoolean(false);
    public static Integer size = graph[0].length; //nr of vertices

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        List<Integer> digits = new ArrayList<>(Arrays.asList(0, 1, 3, 4, 2));
//        List<Integer> s = new ArrayList<>(Arrays.asList(0,1,2,3,4));
//        System.out.println(isHamiltonian(digits));

        List<Integer> availableDigits = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            availableDigits.add(i);
        }

        System.out.println(availableDigits.toString());

        int nrThreads = availableDigits.size();
        service = Executors.newFixedThreadPool(nrThreads);

        List<Integer> solution = new ArrayList<>();
        search(solution, availableDigits, nrThreads);

        System.out.println("CAN FIND HAMILTONIAN: atomic - " + found.get());

        service.shutdown();
    }

    /**
     * @param partialSolution - permutation obtained so far
     * @param digits          - digits that were not used in the permutation
     * @param nrThreads       - available threads to use
     */
    private static void search(List<Integer> partialSolution, List<Integer> digits, int nrThreads) {
        //verify if we already found a Hamiltonian path
        if (found.get()) {
            return;
        }
        //This is the case where we completed a permutation
        if (partialSolution.size() == size) {

            boolean hamiltonian = isHamiltonian(partialSolution);
            if (hamiltonian) {
                //if a Hamiltonian path is found we modify the atomic boolean
                System.out.println("IS HAMILTONIAM PPLMMMMMMMM" + partialSolution.toString());
                found = new AtomicBoolean(true);
            }
            return;
        }

        //split the work
        if (nrThreads > 1) {
            List<Future> futures = new ArrayList<>();
            for (int i = 0; i < digits.size(); i++) {
                //add one digit to the partial solution
                List<Integer> partSum2 = new ArrayList<>(partialSolution);
                partSum2.add(digits.get(i));

                //delete the digit that was added to the partial solution
                List<Integer> availableDigits = new ArrayList<>(digits);
                availableDigits.remove(digits.get(i));

                System.out.println("partialSolution2: " + partSum2.toString());
                System.out.println("digits2: " + availableDigits.toString());

                futures.add(service.submit(() -> search(partSum2, availableDigits, nrThreads / digits.size())));
            }

            try {
                for (Future f : futures) {
                    f.get();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {

            //add a new digit to the permutation
            for (int i = 0; i < digits.size(); i++) {
                //update partial solution
                List<Integer> partSum2 = new ArrayList<>(partialSolution);
                partSum2.add(digits.get(i));
                //delete the digit passed
                List<Integer> digits2 = new ArrayList<>(digits);
                digits2.remove(digits.get(i));

//                System.out.println("partialSolution2: " + partSum2.toString());
//                System.out.println("digits2: " + digits2.toString());

                //recursive call --not necessary
                search(partSum2, digits2, nrThreads);
            }
        }
    }

    public static boolean isHamiltonian(List<Integer> permutation) {
        for (int i = 0; i < size - 1; i++) {
            Integer vertex1 = permutation.get(i);
            Integer vertex2 = permutation.get(i + 1);
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