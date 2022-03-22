package dk.easv.GUI.Model;

import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.BLL.PersonManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class PersonModel {

    private PersonManager pM;
    private ObservableList<Person> eventmanagerList;

    public PersonModel() throws IOException {
        pM = new PersonManager();
        eventmanagerList = FXCollections.observableArrayList();
        eventmanagerList.addAll(getAllEventmanagers());
    }

    private List<Person> getAllEventmanagers()
    {
        return pM.getAllEventManagers();
    }

    public ObservableList<Person> getObservablePersons() throws IOException {
        eventmanagerList.setAll(getAllEventmanagers());
        return eventmanagerList;
    }

    public Person loginPerson(String email, String password){
        return pM.loginPerson(email,password);
    }

    public void createEventmanager(String name, String email, String password) {
        pM.createEventManager(name,email,password);
    }


    public void deleteEventmanager(EventManager eventManager, PersonType usertype) {
        pM.delete(eventManager, PersonType.EVENTMANAGER);}

    public void updateEventmanager(EventManager eventManager) {
        pM.updateEventmanager(eventManager);
    }

}
