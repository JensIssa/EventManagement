package dk.easv.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.AgeGroup;
import dk.easv.BE.Event;
import dk.easv.BE.User;
import dk.easv.DAL.TicketDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TicketManager {
    TicketDAO ticketDAO;

    public TicketManager() throws IOException {
        ticketDAO = new TicketDAO();
    }

    public List<Integer> getTicketId(User user) throws SQLServerException {
        return ticketDAO.getTicketId(user);
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

    public int countIndividualAdult(User user){
        return ticketDAO.countIndividualGuestAttendees(user, AgeGroup.ADULT);
    }
    public int countIndividualOldKid(User user){
        return ticketDAO.countIndividualGuestAttendees(user, AgeGroup.OLDKID);
    }
    public int countIndividualYoungKid(User user){
        return ticketDAO.countIndividualGuestAttendees(user, AgeGroup.YOUNGERKID);
    }

    public void updateTicketAdult(User user, Event event, int ticketAmount) throws SQLException {
        ticketDAO.updateTicket(user, event, AgeGroup.ADULT,ticketAmount);
    }
    public void updateTicketOldKid(User user, Event event, int ticketAmount) throws SQLException {
        ticketDAO.updateTicket(user, event, AgeGroup.OLDKID,ticketAmount);
    }

    public void updateTicketYoungKid(User user, Event event, int ticketAmount) throws SQLException {
        ticketDAO.updateTicket(user, event, AgeGroup.YOUNGERKID,ticketAmount);
    }
}
