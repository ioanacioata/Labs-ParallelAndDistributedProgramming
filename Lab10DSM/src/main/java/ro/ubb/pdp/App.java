package ro.ubb.pdp;

import com.hazelcast.core.IMap;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;

import java.util.concurrent.ExecutionException;

public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        JetInstance jet = Jet.newJetInstance();

        IMap<String, Integer> lines = jet.getMap("lines");
        lines.addEntryListener(new ValueChangeListener(), true);

        JetInstance jet1 = Jet.newJetInstance();

        IMap<String, Integer> lines1 = jet1.getMap("lines");

        JetInstance jet2 = Jet.newJetInstance();

        IMap<String, Integer> lines2 = jet2.getMap("lines");

        JetInstance jet3 = Jet.newJetInstance();

        IMap<String, Integer> lines3 = jet3.getMap("lines");

        lines1.put("value", 7);
        lines2.put("value", 8);
        lines3.put("value", 9);
        lines3.put("value", 12);
        lines2.put("value", 11);
        lines1.put("value", 10);
    }
}