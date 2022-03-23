package dk.easv.GUI.Model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;
import dk.easv.BE.EventManager;
import dk.easv.BE.User;
import dk.easv.BLL.EventBusinessManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EventModel {
    private EventBusinessManager eBM;
    private ObservableList<Event> eventsList;
    private ObservableList<User> usersInEventsList;

    public EventModel() throws IOException, SQLException {
        eBM = new EventBusinessManager();
        eventsList = FXCollections.observableArrayList();
        eventsList.addAll(eBM.getAllEvents());
        usersInEventsList = FXCollections.observableArrayList();
        usersInEventsList.addAll(getAllUsersFromEvent(new Event(0, null, null, null, null)));
    }

    private List<Event> getAllEvents() throws SQLException {return eBM.getAllEvents();}

    public ObservableList<Event> getObservableEvents() throws SQLException {
        eventsList.setAll(getAllEvents());
        return eventsList;
    }

    public void createEvent(EventManager eventManager, String name, LocalDate startDate, String startTime, String info) throws SQLServerException {
        eBM.createEvent(eventManager,name,startDate,startTime,info);
    }

    public void deleteEvent(Event eventDelete){
        eBM.deleteEvent(eventDelete);
    }

    public List<User> getAllUsersFromEvent(Event event){
       return eBM.getAllUsersFromEvent(event);
    }

    public ObservableList<User> getObservableUsersFromEvents(Event event){
        usersInEventsList.setAll(getAllUsersFromEvent(event));
        return usersInEventsList;
    }

    public void addUserToEvent(Event event, User user){
        eBM.addUserToEvent(event, user);
    }

    public void updateEvent(Event event){
        eBM.updateEvent(event);
    }
}
