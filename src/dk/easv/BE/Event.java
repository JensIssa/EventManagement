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
    private String endTime;
    private String managerName;
    private String info;
    private String loc;

    public Event(int id,int managerId, String name, LocalDate startDate, String startTime, String managerName, String info,String endTime, String loc) {
        this.id = id;
        this.managerId = managerId;
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.managerName = managerName;
        this.info = info;
        this.loc = loc;
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

    public String getManagerName() {return managerName;}

    public String getStartTime() {
        return startTime;
    }

    public String getInfo() {
        return info;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLoc() {
        return loc;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", managerId=" + managerId +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", managerName='" + managerName + '\'' +
                ", info='" + info + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
