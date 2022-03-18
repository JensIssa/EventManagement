package dk.easv.BLL;

import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.DAL.PersonDAO;

import java.awt.desktop.PreferencesEvent;
import java.io.IOException;
import java.util.List;

public class PersonManager {
    PersonDAO personDAO;
    public PersonManager() throws IOException {
        personDAO = new PersonDAO();
    }

    public Person loginPerson(String email, String password){
        //return en manager / admin / user
        return personDAO.loginUser(email,password);
    }
    public List<Person> getAllUsers(){
        return personDAO.getAllUsers();
    }
    public List<Person> getAllEventManagers(){
        return personDAO.getAllEventManagers();
    }
    public List<Person> getAllAdmins(){
        return personDAO.getAllAdmins();
    }
}
