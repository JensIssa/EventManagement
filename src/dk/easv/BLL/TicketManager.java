package dk.easv.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.AgeGroup;
import dk.easv.BE.Event;
import dk.easv.DAL.TicketDAO;

import java.io.IOException;
import java.sql.SQLException;

public class TicketManager {
    TicketDAO ticketDAO;

    public TicketManager() throws IOException {
        ticketDAO = new TicketDAO();
    }

    public int getTicketId() throws SQLServerException {
        return ticketDAO.getTicketId();
    }
    public int countEventAttendees(Event event) throws SQLException {
       int total = ticketDAO.countEventAttendees(event, AgeGroup.ADULT) + ticketDAO.countEventAttendees(event, AgeGroup.OLDKID) + ticketDAO.countEventAttendees(event, AgeGroup.YOUNGERKID);
        return total;

    }
    public int countAdults(Event event) throws SQLException {
        return ticketDAO.countEventAttendees(event, AgeGroup.ADULT);
    }
    public int countOlderKids(Event event) throws SQLException {
        return ticketDAO.countEventAttendees(event, AgeGroup.OLDKID);
    }
    public int countYoungerKids(Event event) throws SQLException {
        return ticketDAO.countEventAttendees(event, AgeGroup.YOUNGERKID);
    }
}
