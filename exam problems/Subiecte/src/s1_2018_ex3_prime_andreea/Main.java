package s1_2018_ex3_prime_andreea;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Integer N = 100;
    //Prime numbers between 1<->sqrt(N)
    public static List<Integer> primeNrsSqrt = new ArrayList<>();
    public static final List<Integer> numbers = new ArrayList<>();

    public static void findPrimes() {
        //Serially find all prime numbers until sqrt(N)
        int p = 0;
        for (int i = 2; i <= Math.sqrt(N); i++) {
            p = 0;
            for (int j = 2; j < i; j++) {
                if (i % j == 0)
                    p = 1;
            }
            if (p == 0) {
                primeNrsSqrt.add(i);
            }
        }
    }

    public void run() throws InterruptedException {
        int nrThreads = primeNrsSqrt.size();

        ExecutorService service = Executors.newFixedThreadPool(nrThreads);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < nrThreads; i++) {
            service.submit(new FindMultiples(primeNrsSqrt.get(i), numbers));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();

        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Time Elapsed: " + (endTime - startTime));

        System.out.println("Prime numbers: ");
        for (int i = 0; i < numbers.size(); i++) {
            if(numbers.get(i) != 1){
                System.out.println(numbers.get(i)+" ");
            }
//            System.out.print(numbers.get(i) == 1 ? "" : numbers.get(i) + " ");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello! :)");

        Main.findPrimes();
        for (int i = 2; i <= N; i++) {
            numbers.add(i);
        }

        Main main = new Main();
        main.run();
    }
}


