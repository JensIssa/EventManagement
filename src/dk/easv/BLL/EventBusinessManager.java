package dk.easv.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.*;
import dk.easv.BLL.util.SearchUtil;
import dk.easv.DAL.EventDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
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

    public void createEvent(EventManager eventManager, String name, LocalDate startDate, String startTime, String info, String endTime, String loc) throws SQLServerException {
        eventDAO.createEvent(eventManager, name, startDate, startTime, info, endTime, loc);
    }
    public void deleteEvent(Event eventDelete){
        eventDAO.deleteEvent(eventDelete);
    }

    public List<Event> getAllEvents() throws SQLException {return eventDAO.getAllEvents();}


    public void exportUserEmailsFromEvent(Event event) throws IOException {
        FileWriter writer = new FileWriter(new File("emailLister",event.getName() + " EmailListe.txt"));//måske .csv???
        for(String str: eventDAO.getEmailListFromEvent(event)) {
            writer.write(str +","+ System.lineSeparator());
        }
        writer.close();
    }

    public void createTicket(Event event, User user, int adultTickets, int oldChildTickets, int youngChildTickets) throws SQLException {
      if (adultTickets > 0){
          eventDAO.createTicket(event, user, AgeGroup.ADULT, adultTickets);
      }
      if (oldChildTickets > 0){
          eventDAO.createTicket(event, user, AgeGroup.OLDKID, oldChildTickets);
      }
      if (youngChildTickets > 0){
          eventDAO.createTicket(event, user, AgeGroup.YOUNGERKID, youngChildTickets);
      }

    }

    public List<User> getAllUsersFromEvent(Event event){
        return eventDAO.getAllUsersFromEvent(event);
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
