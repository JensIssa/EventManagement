package dk.easv.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;
import dk.easv.BE.EventManager;
import dk.easv.DAL.EventDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EventBusinessManager {
    EventDAO eventDAO;

    public EventBusinessManager() throws IOException {
        eventDAO = new EventDAO();
    }

    public void create(EventManager eventManager, String name, LocalDate startDate, String startTime, String info) throws SQLServerException {
        eventDAO.createEvent(eventManager, name, startDate, startTime, info);
    }

    public List<Event> getAllEvents() throws SQLException {return eventDAO.getAllEvents();}
}
