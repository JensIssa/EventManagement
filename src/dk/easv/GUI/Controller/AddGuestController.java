package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;
import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.BE.User;
import dk.easv.GUI.Model.EventModel;
import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddGuestController extends SuperController implements Initializable, IEventController {
    public Label labelYoungerKids;
    @FXML
    private Label labelAdult;
    @FXML
    private Label labelOlderKids;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField emailTxtField;
    @FXML
    private TextField phonenumberTxtField;
    @FXML
    private TextField passwordTxtField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    private PersonModel personModel;
    private EventModel eventModel;
    private Event event;

    public AddGuestController() throws IOException, SQLException {
        personModel = new PersonModel();
        eventModel = new EventModel();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maxLengthListener(emailTxtField, 150);
        addPhoneNumberListener(phonenumberTxtField);
    }

    public void handleCancelButton() {
        closeWindow(cancelBtn);
    }

    public void handleSaveUser(ActionEvent actionEvent) throws SQLException {



        String userName = getName(nameTxtField);
        String userEmail = getEmail(emailTxtField);
        int userPhoneNumber = getPhoneNumber(phonenumberTxtField);


        int adultCount = Integer.parseInt(labelAdult.getText());
        int bigChildCount = Integer.parseInt(labelOlderKids.getText());
        int smallChildCount = Integer.parseInt(labelYoungerKids.getText());

        if (userName != null && userEmail != null && adultCount > 0) {
            personModel.createuser(userName, userEmail, userPhoneNumber);
            int id = personModel.getMostRecentId();
            User user = new User(id, userName, userEmail, "password", PersonType.USER, userPhoneNumber);
            eventModel.createTicket(event, user, adultCount, bigChildCount, smallChildCount);
            closeWindow(saveBtn);
        } else {
            errorMessage("Udfyld venligst alle felter. Billeten kan ikke oprettes hvis en voksen ikke er tilmeldt.");
        }
    }

    public void handleSubstractOlderKids(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelOlderKids.getText());
        if (count > 0) {
            count--;
            labelOlderKids.setText(String.valueOf(count));
        }
    }

    public void handleAddOlderKids(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelOlderKids.getText());
        count++;
        labelOlderKids.setText(String.valueOf(count));

    }

    public void handleSubstractYoungerKids(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelYoungerKids.getText());
        if (count > 0) {
            count--;
            labelYoungerKids.setText(String.valueOf(count));
        }

    }

    public void handleAddYoungerkids(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelYoungerKids.getText());
        count++;
        labelYoungerKids.setText(String.valueOf(count));
    }

    public void handleAddAdults(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelAdult.getText());
        count++;
        labelAdult.setText(String.valueOf(count));

    }

    public void handleSubstractAdults(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelAdult.getText());
        if (count > 0) {
            count--;
            labelAdult.setText(String.valueOf(count));
        }
    }

    @Override
    public void setPersonInfo(Person person) {

    }

    @Override
    public void setEventInfo(Event event) throws IOException, SQLServerException {
        this.event = event;
    }
}
