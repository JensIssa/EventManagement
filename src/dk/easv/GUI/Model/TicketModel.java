package dk.easv.GUI.Model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.AgeGroup;
import dk.easv.BE.Event;
import dk.easv.BE.User;
import dk.easv.BLL.TicketManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TicketModel {
    private TicketManager ticketManager;

    public TicketModel() throws IOException {
        ticketManager = new TicketManager();
    }

    public List<Integer> getTicketId(User user) throws SQLServerException {
        return ticketManager.getTicketId(user);
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

    public int countIndividualAdult(User user){
        return ticketManager.countIndividualAdult(user);
    }
    public int countIndividualOldKid(User user){
        return ticketManager.countIndividualOldKid(user);
    }
    public int countIndividualYoungKid(User user){
        return ticketManager.countIndividualYoungKid(user);
    }

    public void updateTicketAdult(User user, Event event, int ticketAmount) throws SQLException {
        ticketManager.updateTicketAdult(user, event, ticketAmount);
    }

    public void updateTicketOldKid(User user, Event event, int ticketAmount) throws SQLException {
        ticketManager.updateTicketOldKid(user, event, ticketAmount);
    }

    public void updateTicketYoungKid(User user, Event event, int ticketAmount) throws SQLException {
        ticketManager.updateTicketYoungKid(user, event, ticketAmount);
    }
}
