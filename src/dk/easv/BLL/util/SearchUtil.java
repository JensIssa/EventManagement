package dk.easv.BLL.util;

import dk.easv.BE.Event;
import dk.easv.BE.Person;
import dk.easv.BE.User;
import dk.easv.DAL.EventDAO;
import dk.easv.DAL.PersonDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
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

        public List<Person> searchEventManagers(ObservableList<Person> searchBase, String query){
            List<Person> searchResult = new ArrayList<>();
            if ( query.isEmpty() || query.isBlank()){
                return personDAO.getAllEventManagers();
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
    public List<User> searcUserEvent(ObservableList<User> searchBase, String query, Event event){
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

    public List<Event> searchEvent(ObservableList<Event> searchBase, String query) throws SQLException {
        List<Event> searchResult = new ArrayList<>();
        if (query.isEmpty()){
           return eventDAO.getAllEvents();
        }

        else {
            for (Event event : searchBase){
                if (compareToEventName(query, event)){
                    searchResult.add(event);
                }
            }
        }
        return searchResult;
    }

    private boolean compareToName(String query, Person user){
        return user.getName().toLowerCase().contains(query.toLowerCase().trim());
    }

    private boolean compareToEventName(String query, Event event){
        return event.getName().toLowerCase().contains(query.toLowerCase().trim());
    }
}
