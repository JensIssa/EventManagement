package dk.easv.GUI.Model;

import dk.easv.BE.Event;
import dk.easv.BE.User;
import dk.easv.BLL.EventBusinessManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.Formatter;
import java.util.List;

public class UserEventModel {
    private ObservableList<User> usersInEventsList;
    private EventBusinessManager eventBusinessManager;

    public UserEventModel() throws IOException {
        usersInEventsList = FXCollections.observableArrayList();
        eventBusinessManager = new EventBusinessManager();
    }

    public List<User> getAllUsersFromEvent(Event event) {
        return eventBusinessManager.getAllUsersFromEvent(event);
    }

    public ObservableList<User> getObservableUsersFromEvents(Event event) {
        usersInEventsList.setAll(getAllUsersFromEvent(event));
        return usersInEventsList;
    }

    public void deleteUserFromEvent(User user, Event event) {
        eventBusinessManager.deleteUserFromEvent(user, event);
    }

}
