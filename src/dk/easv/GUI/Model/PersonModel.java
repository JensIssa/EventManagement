package dk.easv.GUI.Model;

import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.BE.User;
import dk.easv.BLL.PersonManager;
import dk.easv.BLL.util.SearchUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class PersonModel {

    private PersonManager pM;
    private ObservableList<Person> eventmanagerList;
    private ObservableList<Person> userList;
    private SearchUtil searchUtil;

    public PersonModel() throws IOException {
        pM = new PersonManager();
        eventmanagerList = FXCollections.observableArrayList();
        eventmanagerList.addAll(getAllEventmanagers());
        userList = FXCollections.observableArrayList();
        userList.addAll(getAllUsers());
        searchUtil = new SearchUtil();
    }

    private List<Person> getAllEventmanagers()
    {
        return pM.getAllEventManagers();
    }

    public ObservableList<Person> getObservablePersons() throws IOException {
        eventmanagerList.setAll(getAllEventmanagers());
        return eventmanagerList;
    }

    public List<Person> getAllUsers() {
        return pM.getAllUsers();
    }

    public ObservableList<Person> getobservableUsers(){
        userList.setAll(getAllUsers());
        return userList;
    }

    public Person loginPerson(String email, String password){
        return pM.loginPerson(email,password);
    }

    public void createEventmanager(String name, String email, String password) {
        pM.createEventManager(name,email,password);
    }

    public ObservableList<Person> searchUsers(ObservableList<Person> users, String searchQuery){
        return  pM.search(users, searchQuery);
    }
    public ObservableList<Person> searchEventManagers(ObservableList<Person> eventManagers, String query){
        return pM.searchEventManagers(eventManagers, query);
    }


    public void deleteEventmanager(EventManager eventManager, PersonType usertype) {
        pM.delete(eventManager, PersonType.EVENTMANAGER);}

    public void updateEventmanager(EventManager eventManager) {
        pM.updateEventmanager(eventManager);
    }

    public void createuser(String name, String email, String password, int phoneNumber){
        pM.createUser(name, email, password, phoneNumber);
    }
    public void updateUser(User user){
        pM.updateUser(user);
    }
}
