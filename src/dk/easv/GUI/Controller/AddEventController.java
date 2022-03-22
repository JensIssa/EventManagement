package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.GUI.Model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddEventController extends SuperController implements IController, Initializable {

    @FXML
    private Button saveBtn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField eventStartTxtfield;
    @FXML
    private TextField informationTxtfield;
    @FXML
    private TextField nameTxtField;

    private EventModel eventModel;
    private EventManager eventManager;

    public AddEventController() throws SQLException, IOException {
        eventModel = new EventModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addNumbersOnlyListener(eventStartTxtfield);
        maxLenghtListener(informationTxtfield);
    }

    public void handleSaveBtn(ActionEvent actionEvent) throws SQLServerException {
        String eventName = nameTxtField.getText();
        LocalDate eventDate = datePicker.getValue();
        String eventStart = eventStartTxtfield.getText();
        String info = informationTxtfield.getText();

        //String eventInfo = informationTxtfield.getText(); // gør ikke noget, men burde blive addet


        if (eventName != null && eventDate != null && validTime(eventStartTxtfield)) {
            eventModel.createEvent(eventManager, eventName, eventDate, eventStart, info);
            closeWindow(saveBtn);
        }

    }

    private boolean validTime(TextField textField){
        if (!textField.getText().isEmpty() && textField.getText().length() == 5) {
            String[] splitString = textField.getText().split(":");
            int[] intArray = {Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1])};
            if (intArray[0] <= 24 && intArray[1] <= 59) {
                return true;
            }
        }
        System.out.println("Invalid time");
        return false;
    }

    public void handleCancelBtn(ActionEvent actionEvent) {
    }

    @Override
    public void setPersonInfo(Person person) {
        eventManager = (EventManager) person;
    }


}
