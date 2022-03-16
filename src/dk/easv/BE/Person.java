package dk.easv.BE;

import java.util.List;

public abstract class Person {
    private int id;
    private String email;
    private String password;
    private PersonType type;
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }
}
