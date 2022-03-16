package dk.easv.BE;

import java.util.List;

public class Admin extends Person {

    private List<EventManager> eventManagers;

    public Admin(int id,String name , String email, String password, PersonType type) {
        super(id, name, email, password, type);
    }

    public List<EventManager> getEventManagers() {
        return eventManagers;
    }
    public void setEventManagers(List<EventManager> eventManagers) {
        this.eventManagers = eventManagers;
    }
}
