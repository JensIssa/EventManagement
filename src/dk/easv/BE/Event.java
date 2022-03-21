package dk.easv.BE;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Event {
    private int id;
    private int managerId;
    private String name;
    private LocalDate startDate;
    private String startTime;

    public Event(int id,int managerId, String name, LocalDate startDate, String startTime) {
        this.id = id;
        this.managerId = managerId;
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", managerId=" + managerId +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}
