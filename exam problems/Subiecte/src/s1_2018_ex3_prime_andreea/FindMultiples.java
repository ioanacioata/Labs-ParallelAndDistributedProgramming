package s1_2018_ex3_prime_andreea;

import java.util.ArrayList;
import java.util.List;

/**
 * Se pune extends Thread cand nu stii numarul de threaduri sau folosesti thread pool
 */
public class FindMultiples extends Thread implements Runnable {

    int primeNr;
    final List<Integer> nrList;

    public FindMultiples(int primeNr, List<Integer> nrList) {
        this.primeNr = primeNr;
        this.nrList = nrList;
    }

    @Override
    public void run() {
        List<Integer> nrs = new ArrayList<>();
        nrs.addAll(nrList);

        for (int i = 0; i < nrList.size(); i++) {
            if (nrList.get(i) != primeNr && nrList.get(i) % primeNr == 0) {
                synchronized (nrList.get(i)) {
                    nrList.set(i, 1);
                }
            }
        }
    }
}