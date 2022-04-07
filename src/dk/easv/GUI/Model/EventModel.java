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

    public EventModel() throws IOException, SQLException {
        eBM = new EventBusinessManager();
        eventsList = FXCollections.observableArrayList();
        eventsList.addAll(eBM.getAllEvents());
    }

    private List<Event> getAllEvents() throws SQLException {return eBM.getAllEvents();}

    public ObservableList<Event> getObservableEvents() throws SQLException {
        eventsList.setAll(getAllEvents());
        return eventsList;
    }

    public void createEvent(EventManager eventManager, String name, LocalDate startDate, String startTime, String info, String endTime, String loc) throws SQLServerException {
        eBM.createEvent(eventManager,name,startDate,startTime,info,endTime,loc);
    }

    public void deleteEvent(Event eventDelete){
        eBM.deleteEvent(eventDelete);
    }

    public void createTicket(Event event, User user, int adultTickes, int oldChildTickets, int youngChildTickets) throws SQLException {
        eBM.createTicket(event, user, adultTickes, oldChildTickets, youngChildTickets);
    }

    public void updateEvent(Event event){
        eBM.updateEvent(event);
    }

    public List<Event> getAllEventsFromUser(User user){
        return eBM.getAllEventsFromUser(user);
    }
    public ObservableList<Event> searchEvents(ObservableList<Event> searchBase, String query) throws SQLException {
        return eBM.searchEvent(searchBase, query);
    }

    public void exportEmailsFromUsers(Event event) throws IOException {
        eBM.exportUserEmailsFromEvent(event);
    }
}
