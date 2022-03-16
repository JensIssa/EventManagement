package dk.easv.BLL;

import dk.easv.BE.PersonType;
import dk.easv.DAL.PersonDAO;

import java.io.IOException;

public class EventManagerManager {
    PersonDAO eventManagerDAO;
    public EventManagerManager() throws IOException {
        eventManagerDAO = new PersonDAO();
    }


    public PersonType getPersonType(String email, String password) {
        return eventManagerDAO.getPersonType(email,password);
    }
}
