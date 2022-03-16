package dk.easv.BLL;

import dk.easv.BE.PersonType;
import dk.easv.DAL.EventManagerDAO;

import java.io.IOException;

public class EventManagerManager {
    EventManagerDAO eventManagerDAO;
    public EventManagerManager() throws IOException {
        eventManagerDAO = new EventManagerDAO();
    }


    public PersonType getPersonType(String email, String password) {
        return eventManagerDAO.getPersonType(email,password);
    }
}
