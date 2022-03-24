package dk.easv.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.*;
import dk.easv.BLL.util.SearchUtil;
import dk.easv.DAL.EventDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EventBusinessManager {
    EventDAO eventDAO;
    SearchUtil searchUtil;

    public EventBusinessManager() throws IOException {
        eventDAO = new EventDAO();
        searchUtil = new SearchUtil();
    }

    public void createEvent(EventManager eventManager, String name, LocalDate startDate, String startTime, String info) throws SQLServerException {
        eventDAO.createEvent(eventManager, name, startDate, startTime, info);
    }
    public void deleteEvent(Event eventDelete){
        eventDAO.deleteEvent(eventDelete);
    }

    public List<Event> getAllEvents() throws SQLException {return eventDAO.getAllEvents();}


    public void createEmailDocFromEvent(Event event) throws IOException {
        //manger connect til knap
        FileWriter writer = new FileWriter(event.getName() + " EmailListe.txt");//måske .csv???
        for(String str: eventDAO.getEmailListFromEvent(event)) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }

    public void addUserToEvent(Event event, User user){
        eventDAO.addUserToEvent(event, user);
    }

    public List<User> getAllUsersFromEvent(Event event){
        return eventDAO.getAllUsersFromEvent(event);
    }
    //test main til at lave en email list fil
    public static void main(String[] args) throws IOException, SQLException {
        EventBusinessManager eventBusinessManager = new EventBusinessManager();
        List<Event> eventlist = eventBusinessManager.getAllEvents();
        Event event = eventlist.get(2);

        eventBusinessManager.createEmailDocFromEvent(event);
        System.out.println("Event ID: " + event.getId());
    }
    public void updateEvent(Event event){
        eventDAO.updateEvent(event);;
    }
    public void deleteUserFromEvent(User user,Event event){
        eventDAO.deleteUserFromEvent(user,event);
    }

    public List<Event> getAllEventsFromUser(User user){
        return eventDAO.getAllEventsFromUser(user);
    }


    public ObservableList<Event> searchEvent(ObservableList<Event> searchBase, String query) throws SQLException {
        ObservableList<Event> foundEvents = FXCollections.observableArrayList();
        foundEvents.addAll(searchUtil.searchEvent(searchBase, query));
        return foundEvents;
    }
}
