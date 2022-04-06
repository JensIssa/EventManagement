package dk.easv.GUI.Model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.AgeGroup;
import dk.easv.BE.Event;
import dk.easv.BLL.TicketManager;

import java.io.IOException;
import java.sql.SQLException;

public class TicketModel {
    private TicketManager ticketManager;

    public TicketModel() throws IOException {
        ticketManager = new TicketManager();
    }

    public int getTicketId() throws SQLServerException {
        return ticketManager.getTicketId();
    }
    public int countEventAttendees(Event event) throws SQLException {
        return ticketManager.countEventAttendees(event);
    }
    public int countAdults(Event event) throws SQLException {
        return ticketManager.countAdults(event);
    }
    public int countOlderKids(Event event) throws SQLException {
        return ticketManager.countOlderKids(event);
    }
    public int countYoungerKids(Event event) throws SQLException {
        return ticketManager.countYoungerKids(event);
    }
}
