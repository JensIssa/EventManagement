package dk.easv.BLL;

import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.DAL.PersonDAO;

import java.io.IOException;

public class PersonManager {
    PersonDAO personDAO;
    public PersonManager() throws IOException {
        personDAO = new PersonDAO();
    }

    public Person loginPerson(String email, String password){
        return personDAO.loginUser(email,password);
    }
}
