package ro.ubb.pdp;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.IMap;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.map.listener.EntryUpdatedListener;

public class ValueChangeListener implements EntryUpdatedListener<String, Integer> {

    private final int threshold = 10;

    @Override
    public void entryUpdated(EntryEvent<String, Integer> event) {
        Integer value = event.getValue();

        System.out.println("NEW VALUE: " + value);
        System.out.println(event.getMember());

        if (value == threshold) {
            System.out.println("THRESHOLD REACHED");
            JetInstance jet = Jet.newJetInstance();
            IMap<String, Integer> lines = jet.getMap("lines");
            lines.put("value", 11);
        }
    }
}