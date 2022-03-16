package dk.easv.BE;

import java.util.List;

public class Admin extends Person {

    private List<EventManager> eventManagers;

    public List<EventManager> getEventManagers() {
        return eventManagers;
    }
    public void setEventManagers(List<EventManager> eventManagers) {
        this.eventManagers = eventManagers;
    }
}
