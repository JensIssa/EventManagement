package dk.easv.BLL.util;

import dk.easv.BE.Event;
import dk.easv.BE.Person;
import dk.easv.BE.User;
import dk.easv.DAL.EventDAO;
import dk.easv.DAL.PersonDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchUtil {

    public PersonDAO personDAO;
    public EventDAO eventDAO;

    public SearchUtil() throws IOException {
        personDAO = new PersonDAO();
        eventDAO = new EventDAO();
    }

    //TODO FIX SO IT DOESN'T TAKE INPUT FROM A DAO
    public List<Person> search(ObservableList<Person> searchBase, String query){
        List<Person> searchResult = new ArrayList<>();
       if ( query.isEmpty()){
           return personDAO.getAllUsers();
       }
       else {
           for (Person user : searchBase) {
               if (compareToName(query, user)) {
                   searchResult.add(user);
               }
           }
       }
        return searchResult;
        }

//TODO FIX SO IT DOESN'T TAKE INPUT FROM A DAO
    public List<User> searcUserEventh(ObservableList<User> searchBase, String query, Event event){
        List<User> searchResult = new ArrayList<>();
        if ( query.isEmpty()){
            return eventDAO.getAllUsersFromEvent(event);
        }
        else {
            for (User user : searchBase) {
                if (compareToName(query, user)) {
                    searchResult.add(user);
                }
            }
        }
        return searchResult;
    }
    private boolean compareToName(String query, Person user){
        return user.getName().toLowerCase().contains(query.toLowerCase());
    }
}
