import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Alexandra-Ioana on 11/29/2017.
 */
public class HamiltonianCycle {
    private int[][] adjancencyMatrix;
    private int n;
    private int[] allNodes;
    private int[] solution;
    private AtomicBoolean found;
    private ExecutorService service;

    public HamiltonianCycle(String filename) {
        readFromFile(filename);
        allNodes = new int[n];
        for (int i = 0; i < n; ++i) {
            allNodes[i] = i;
        }
        found = new AtomicBoolean(false);
    }

    public void solve(int nThreads) {
//        for(int i = 0; i < n; ++i) {
//            System.out.println(Arrays.toString(adjancencyMatrix[i]));
//        }
        int[] solution = new int[n];
        Arrays.fill(solution, -1);
        service = Executors.newFixedThreadPool(nThreads);
        search(solution, -1, nThreads);
        service.shutdownNow();
    }

    public void search(int[] partialSolution, int position, int nThreads) {
//        System.out.println(Arrays.toString(partialSolution));
        if (found.get())
            return;
        if (position == n - 1) {
            if (adjancencyMatrix[partialSolution[n - 1]][partialSolution[0]] == 1) {
                // solution found
                boolean oldValue = found.getAndSet(true);
                if (!oldValue) {
                    solution = Arrays.copyOf(partialSolution, n);
                }
            }
            return;
        }
        int[] validNodes;
        if (position == -1) {
            validNodes = allNodes;
        } else {
            int[] existentNodes = new int[n];
            for (int i = 0; i <= position; ++i) {
                existentNodes[partialSolution[i]] = 1;
            }
            int currentNode = partialSolution[position];
//            System.out.println("All: " + Arrays.toString(allNodes));
//            System.out.println("existent " + Arrays.toString(existentNodes));
            validNodes = Arrays.stream(allNodes)
                .filter(v -> existentNodes[v] == 0 && adjancencyMatrix[currentNode][v] == 1).toArray();
        }

//        System.out.println("valid " + Arrays.toString(validNodes));

        if (nThreads > 1) {
            List<Future> futures = new ArrayList<>();
            for (int node : validNodes) {
                int[] copy = Arrays.copyOf(partialSolution, n);
                copy[position + 1] = node;
                futures.add(service.submit(() -> {
                    search(copy, position + 1, nThreads / validNodes.length);
                }));
            }

            try {
                for (Future f : futures) {
                    f.get();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            for (int node : validNodes) {
                int[] copy = Arrays.copyOf(partialSolution, partialSolution.length);
                copy[position + 1] = node;
                search(copy, position + 1, 1);
            }
        }
    }

    private void readFromFile(String filename) {
        Path path = Paths.get(filename);
        try {
            List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
            n = Integer.parseInt(lines.get(0));
            adjancencyMatrix = new int[n][n];
            lines.subList(1, lines.size()).forEach(line -> {
                String[] nodes = line.split(" ");
                adjancencyMatrix[Integer.parseInt(nodes[0])][Integer.parseInt(nodes[1])] = 1;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] getSolution() {
        return solution;
    }
}
