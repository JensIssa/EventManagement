package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;
import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.BE.User;
import dk.easv.GUI.Model.EventModel;
import dk.easv.GUI.Model.PersonModel;
import dk.easv.GUI.Model.UserEventModel;
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


public class EventManagerViewController extends SuperController implements Initializable, IController {

    public ComboBox<Event> eventComboBox;
    public Label eventNameLabel;
    public Label startTimeLabel;
    public Label locationLabel;
    public Label dateLabel;
    public Label endTimeLabel;
    public TextArea infoTextArea;
    @FXML
    private TableColumn<User, String> nameColumnuser;
    @FXML
    private TableColumn<User, String> emailColumnUser;
    @FXML
    private TableView<User> userTable;
    @FXML
    private Button closeBtn;
    @FXML
    private TextField searchFieldUsers;

    private EventManager eventManager;
    private PersonModel personModel;
    private EventModel eventModel;
    UserEventModel userEventModel;
    private Event event;

    public EventManagerViewController() throws IOException, SQLException {
        personModel = new PersonModel();
        eventModel = new EventModel();
        userEventModel = new UserEventModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumnuser.setCellValueFactory(new PropertyValueFactory<>("Name"));
        emailColumnUser.setCellValueFactory(new PropertyValueFactory<>("Email"));
        infoTextArea.setWrapText(true);
        clearLabels();
        try {
            eventComboBox.setItems(eventModel.getObservableEvents());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPersonInfo(Person person) {
        eventManager = (EventManager) person;
    }

    public void handleInspect(ActionEvent actionEvent) throws IOException, SQLException {
        if (event != null) {
            openNewSceneWithEventPerson(event, eventManager, "/dk/easv/GUI/View2/EditEventsView.fxml", "Rediger event");
            eventComboBox.setItems(eventModel.getObservableEvents());
            clearLabels();
        }else
            errorMessage("Vælg et event og prøv igen");
    }

    public void handleAddEvent(ActionEvent actionEvent) throws IOException, SQLException {
        openNewSceneWithPerson(eventManager, "/dk/easv/GUI/View2/AddEvent.fxml", "skab event");
        eventComboBox.setItems(eventModel.getObservableEvents());
        clearLabels();
    }

    public void handleAddGuest(ActionEvent actionEvent) throws IOException, SQLServerException {
        event = eventComboBox.getSelectionModel().getSelectedItem();
        if (event != null){
            openNewSceneWithEventPerson(event,eventManager,"/dk/easv/GUI/View2/AddGuestView.fxml","skab gæst");
        }


        if (event != null) {
            userTable.setItems(userEventModel.getObservableUsersFromEvents(event));
        }
    }

    public void handleEditGuest(ActionEvent actionEvent) throws IOException {
        User user = (User) userTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            openNewSceneWithPerson(user, "/dk/easv/GUI/View2/EditGuestView.fxml", "Rediger guest");

        } else {
            errorMessage("Please pick a guest to edit");
        }
        if (event != null) {
            userTable.setItems(userEventModel.getObservableUsersFromEvents(event));
        }
    }


    public void handleDeleteEvent(ActionEvent actionEvent) {
        if (eventComboBox.getSelectionModel().getSelectedItem() != null) {
            Event event = eventComboBox.getSelectionModel().getSelectedItem();
            if (confirmationBox("Er du sikker på at du vil slette " + event.getName() + " ?").get() == ButtonType.YES) {
                eventModel.deleteEvent(event);
                eventComboBox.getItems().remove(event);
            }
        } else {
            errorMessage("vælg et event du ønsker at slette");
        }
    }

    public void handleSearch(KeyEvent keyEvent) {
        //TODO FIX
    }

    public void handleClose(ActionEvent actionEvent) throws IOException {
        closeWindow(closeBtn);
        openScene("/dk/easv/GUI/View/LoginView.fxml", false, "Loginscreen", false);
    }

    public void handleDeleteGuest(ActionEvent actionEvent) throws IOException {
        if (userTable.getSelectionModel().getSelectedItem() != null) {
            User user = userTable.getSelectionModel().getSelectedItem();
            if (confirmationBox("Er du sikker på at du vil slette " + user.getName() + "?").get() == ButtonType.YES) {
                personModel.deleteUser(user);
            }
        } else {
            errorMessage("Vælg den bruger du ønsker at slette");
        }

    }

    public void handleComboBoxClicked(ActionEvent actionEvent) throws IOException {
        event = eventComboBox.getSelectionModel().getSelectedItem();
        if (event != null) {
            clearLabels();
            eventNameLabel.setText(event.getName());
            startTimeLabel.setText(startTimeLabel.getText() + event.getStartTime());
            endTimeLabel.setText(endTimeLabel.getText() + event.getEndTime());
            locationLabel.setText(locationLabel.getText() + event.getLoc());
            dateLabel.setText(dateLabel.getText() + event.getStartDate().toString());
            userTable.setItems(userEventModel.getObservableUsersFromEvents(event));
            infoTextArea.setText(event.getInfo());
        }
    }

    private void clearLabels() {
        eventNameLabel.setText("");
        startTimeLabel.setText("Start tid: ");
        endTimeLabel.setText("Slut tid: ");
        locationLabel.setText("Lokation: ");
        dateLabel.setText("Dato: ");
        userTable.getItems().clear();
    }
}
