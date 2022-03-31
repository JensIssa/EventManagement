package dk.easv.GUI.Controller;

import dk.easv.BE.Event;
import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.BE.User;
import dk.easv.GUI.Model.EventModel;
import dk.easv.GUI.Model.PersonModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class EventManagerViewController extends SuperController  implements Initializable, IController{

    //FIXME PERSON -> USER FOR TABLEVIEW / COULUMNS / SEARCH
    @FXML
    private TableColumn<Person, String> nameColumnuser;
    @FXML
    private TableColumn<Person, String> emailColumnUser;
    @FXML
    private TableView<Person> userTable;
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> eventName;
    @FXML
    private TableColumn<Event, LocalDate> dateStart;
    @FXML
    private TableColumn<Event, String> timeStart;
    @FXML
    private Label eventManagerNameLabel;
    @FXML
    private Button closeBtn;
    @FXML
    private TextField searchFieldUsers;

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
    //TODO FIX USERTABLE TAKING PERSON INPUTS AND TAKE CORRECT VALUES WITH Nam
    public void handleAddUserToEvent(ActionEvent actionEvent) {
    /*    User user = (User) userTable.getSelectionModel().getSelectedItem();
        Event event = (Event) eventTable.getSelectionModel().getSelectedItem();
        if (user != null && event != null){
            eventModel.addUserToEvent(event, user);
            errorMessage(user.getName() + " er blevet tilføjet til begivenheden: " + event.getName());
        }
        else {
            errorMessage("Please select an user and/or event to add an user to the event");
        }

     */
    }

    public void handleAddEvent(ActionEvent actionEvent) throws IOException, SQLException {
        openNewSceneWithPerson(eventManager,"/dk/easv/GUI/View2/AddEvent.fxml","Add Event");
        eventTable.setItems(eventModel.getObservableEvents());
    }

    public void handleAddGuest(ActionEvent actionEvent) throws IOException {
        openScene("/dk/easv/GUI/View2/AddGuestView.fxml", true, "Add guest", true);
        userTable.setItems(personModel.getobservableUsers());
    }

    //TODO FIX USERTABLE TAKING PERSON INPUTS
    public void handleEditGuest(ActionEvent actionEvent) throws IOException {
       /*
        User user = (User) userTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            openNewSceneWithPerson(user, "/dk/easv/GUI/View2/EditGuestView.fxml", "Rediger guest");

        }else{
            errorMessage("Please pick a guest to edit");
        }
        userTable.setItems(personModel.getobservableUsers());

        */
    }


    public void handleDeleteEvent(ActionEvent actionEvent) {
        if (eventTable.getSelectionModel().getSelectedItem() != null){
            Event event = eventTable.getSelectionModel().getSelectedItem();
            if (confirmationBox("Er du sikker på at du vil slette " + event.getName() + " ?").get()== ButtonType.YES){
                eventModel.deleteEvent(event);
                eventTable.getItems().remove(event);
                eventTable.getSelectionModel().clearSelection();
            }
        }else
        {
            errorMessage("vælg et event du ønsker at slette");
        }
    }

    public void handleSearch(KeyEvent keyEvent){
        String searchParam = searchFieldUsers.getText();
        ObservableList<Person> foundUserList =  personModel.searchUsers(userTable.getItems(), searchParam);
        userTable.setItems(foundUserList);
    }

    public void handleClose(ActionEvent actionEvent) throws IOException {
        closeWindow(closeBtn);
        openScene("/dk/easv/GUI/View/LoginView.fxml",false, "Loginscreen",false);
    }

    //TODO FIX USERTABLE TAKING PERSON INPUTS
    public void handleDeleteGuest(ActionEvent actionEvent) throws IOException {
        /*
        if (userTable.getSelectionModel().getSelectedItem()!= null){
            User user = (User) userTable.getSelectionModel().getSelectedItem();
            if (confirmationBox("Er du sikker på at du vil slette " + user.getName() + "?").get() == ButtonType.YES){
                personModel.deleteUser(user);
                userTable.setItems(personModel.getObservablePersons());
            }
        }
        else
        {
            errorMessage("Vælg den bruger du ønsker at slette");
        }
*/
    }
}
