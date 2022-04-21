package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.*;
import dk.easv.GUI.Model.PersonModel;
import dk.easv.GUI.Model.TicketModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditGuestController extends SuperController implements Initializable, IEventController {
    @FXML
    private Label labelYoungerKids;
    @FXML
    private Label labelOlderKids;
    @FXML
    private Label labelAdult;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private TextField phoneNumberTxtField;
    @FXML
    private TextField emailTxtField;
    @FXML
    private TextField nameTxtField;

    private PersonModel personModel;
    private User user = null;
    private TicketModel ticketModel;
    private Event event;

    public EditGuestController() throws IOException {
        personModel = new PersonModel();
        ticketModel = new TicketModel();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maxLengthListener(emailTxtField,150);
        maxLengthListener(nameTxtField,80);
        addPhoneNumberListener(phoneNumberTxtField);
    }

    @Override
    public void setPersonInfo(Person person) {
        this.user = (User) person;
        nameTxtField.setText(user.getName());
        emailTxtField.setText(user.getEmail());
        phoneNumberTxtField.setText(Integer.toString(user.getPhoneNumber()));
        setAllLabels();
    }




    public void handleSaveGuest(ActionEvent actionEvent) throws SQLException {
        String name = getName(nameTxtField);
        String email = getEmail(emailTxtField);
        String password = ".";
        int phoneNumber = getPhoneNumber(phoneNumberTxtField);

        int adultCount = Integer.parseInt(labelAdult.getText());
        int oldKidCount = Integer.parseInt(labelOlderKids.getText());
        int youngKidCount = Integer.parseInt(labelYoungerKids.getText());


        if (name != null && email != null && adultCount > 0)
        {
            int id = user.getId();
            User user = new User(id, name, email, password ,PersonType.USER , phoneNumber);
            ticketModel.updateTicketAdult(user, event, adultCount);
            ticketModel.updateTicketOldKid(user, event, oldKidCount);
            ticketModel.updateTicketYoungKid(user, event, youngKidCount);
            personModel.updateUser(user);
            closeWindow(saveBtn);
        }
        else {
            errorMessage("Udfyld venligst alle felter. Billeten kan ikke oprettes hvis en voksen ikke er tilmeldt.");
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeWindow(cancelBtn);
    }


    public void handleSubstractAdults(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelAdult.getText());
        if (count > 0) {
            count--;
            labelAdult.setText(String.valueOf(count));
        }
    }

    public void handleAddAdults(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelAdult.getText());
        count++;
        labelAdult.setText(String.valueOf(count));
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

    private void setAllLabels(){
       int adultCount = ticketModel.countIndividualAdult(user);
       int oldKidCount = ticketModel.countIndividualOldKid(user);
       int youngkidCount = ticketModel.countIndividualYoungKid(user);

       labelAdult.setText(String.valueOf(adultCount));
       labelOlderKids.setText(String.valueOf(oldKidCount));
       labelYoungerKids.setText(String.valueOf(youngkidCount));
    }

    @Override
    public void setEventInfo(Event event) throws IOException, SQLServerException {
        this.event = event;

    }
}
