package dk.easv.BE;

import java.util.List;

public class Admin {
    private int id;
    private String username;
    private String password;
    private List<EventManager> eventManagers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<EventManager> getEventManagers() {
        return eventManagers;
    }

    public void setEventManagers(List<EventManager> eventManagers) {
        this.eventManagers = eventManagers;
    }
}
