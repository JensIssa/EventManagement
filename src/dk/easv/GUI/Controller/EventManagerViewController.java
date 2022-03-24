package dk.easv.GUI.Controller;

import dk.easv.BE.Event;
import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.BE.User;
import dk.easv.GUI.Model.EventModel;
import dk.easv.GUI.Model.PersonModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class EventManagerViewController extends SuperController  implements Initializable, IController{

    public TableColumn<Person, String> nameColumnuser;
    public TableColumn<Person, String> emailColumnUser;
    public TableView<Person> userTable;
    public TableView<Event> eventTable;
    public TableColumn<Event, String> eventName;
    public TableColumn<Event, LocalDate> dateStart;
    public TableColumn<Event, String> timeStart;
    public Label eventManagerNameLabel;
    public TextField searchFieldUsers;
    private EventManager eventManager;
    private PersonModel personModel;
    private EventModel eventModel;

    public EventManagerViewController() throws IOException, SQLException {
        personModel = new PersonModel();
        eventModel = new EventModel();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumnuser.setCellValueFactory(new PropertyValueFactory<>("Name"));
        emailColumnUser.setCellValueFactory(new PropertyValueFactory<>("Email"));
        userTable.setItems(personModel.getobservableUsers());
        eventName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        dateStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        timeStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        try {
            eventTable.setItems(eventModel.getObservableEvents());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setPersonInfo(Person person) {
        eventManager = (EventManager) person;
        eventManagerNameLabel.setText(eventManager.getName());
    }

    public void handleInspect(ActionEvent actionEvent) throws IOException, SQLException {
        Event event = (Event) eventTable.getSelectionModel().getSelectedItem();
        if (event != null){
            openNewSceneWithEventPerson(event,eventManager ,"/dk/easv/GUI/View2/EditEventsView.fxml", "Edit events" );
        }
        else {
            errorMessage("Please select an Event to edit");
        }
        eventTable.getItems().clear();
        eventTable.setItems(eventModel.getObservableEvents());
    }

    public void handleAddUserToEvent(ActionEvent actionEvent) {
        User user = (User) userTable.getSelectionModel().getSelectedItem();
        Event event = (Event) eventTable.getSelectionModel().getSelectedItem();
        if (user != null && event != null){
            eventModel.addUserToEvent(event, user);
        }
        else {
            errorMessage("Please select an user and/or event to add an user to the event");
        }
    }

    public void handleAddEvent(ActionEvent actionEvent) throws IOException {
        openNewSceneWithPerson(eventManager,"/dk/easv/GUI/View2/AddEvent.fxml","Add Event");
    }

    public void handleAddGuest(ActionEvent actionEvent) throws IOException {
        openScene("/dk/easv/GUI/View2/AddGuestView.fxml", true, "Add guest", true);
        userTable.setItems(personModel.getobservableUsers());
    }

    public void handleEditGuest(ActionEvent actionEvent) throws IOException {

        User user = (User) userTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            openNewSceneWithPerson(user, "/dk/easv/GUI/View2/EditGuestView.fxml", "Rediger guest");

        }else{
            errorMessage("Please pick a guest to edit");
        }
        userTable.setItems(personModel.getobservableUsers());
    }


    public void handleDeleteEvent(ActionEvent actionEvent) {
        Event eventToDelete = eventTable.getSelectionModel().getSelectedItem();
        eventModel.deleteEvent(eventToDelete);
        eventTable.getItems().remove(eventToDelete);
    }

    public void handleSearch(KeyEvent keyEvent){
        String searchParam = searchFieldUsers.getText();
        ObservableList<Person> foundUserList =  personModel.searchUsers(userTable.getItems(), searchParam);
        userTable.setItems(foundUserList);
    }
}
