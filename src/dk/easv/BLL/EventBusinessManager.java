package dk.easv.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;
import dk.easv.BE.EventManager;
import dk.easv.BE.PersonType;
import dk.easv.BE.User;
import dk.easv.DAL.EventDAO;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EventBusinessManager {
    EventDAO eventDAO;

    public EventBusinessManager() throws IOException {
        eventDAO = new EventDAO();
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
    public void deleteUserFromEvent(User user){
        eventDAO.deleteUserFromEvent(user);
    }
}
