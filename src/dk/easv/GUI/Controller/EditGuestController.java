package dk.easv.GUI.Controller;

import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.BE.User;
import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditGuestController extends SuperController implements Initializable, IController {
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

    public EditGuestController() throws IOException {
        personModel = new PersonModel();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maxLenghtListener(emailTxtField,150);
        maxLenghtListener(nameTxtField,80);
        addPhoneNumberListener(phoneNumberTxtField);
    }

    @Override
    public void setPersonInfo(Person person) {
        this.user = (User) person;
        nameTxtField.setText(user.getName());
        emailTxtField.setText(user.getEmail());
        phoneNumberTxtField.setText(Integer.toString(user.getPhoneNumber()));
    }




    public void handleSaveGuest(ActionEvent actionEvent) {
        String name = getName(nameTxtField);
        String email = getEmail(emailTxtField);
        String password = ".";
        int phoneNumber = getPhoneNumber(phoneNumberTxtField);
        if (name != null && email != null)
        {
            int id = user.getId();
            User user = new User(id, name, email, password ,PersonType.USER , phoneNumber);
            personModel.updateUser(user);
            closeWindow(saveBtn);
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeWindow(cancelBtn);
    }


}
