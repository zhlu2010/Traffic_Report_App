package Example;

import java.util.ArrayList;
import java.util.List;

public class Example_DataService {

    public static List<Event> getEventNames(int n) {
        List<Event> events = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            events.add(new Event("Event" + i, "745 10th street, NJ", "This is a huge event"));
        }
        return events;
    }
}
