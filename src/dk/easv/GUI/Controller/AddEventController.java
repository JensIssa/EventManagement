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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddEventController extends SuperController implements IController, Initializable {


    @FXML
    private TextField eventEndTxtfield;
    @FXML
    private TextField locationTxtfield;
    @FXML
    private Button saveBtn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField eventStartTxtfield;
    @FXML
    private TextArea txtAreaEmail;
    @FXML
    private TextField nameTxtField;

    private EventModel eventModel;
    private EventManager eventManager;

    public AddEventController() throws SQLException, IOException {
        eventModel = new EventModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maxLengthListener(nameTxtField, 60);
        addTimeListener(eventStartTxtfield);
        addTimeListener(eventEndTxtfield);
        maxLengthListenerTxtArea(txtAreaEmail);
        maxLengthListener(locationTxtfield, 100);
    }

    public void handleSaveBtn(ActionEvent actionEvent) throws SQLServerException {
        String eventName = getName(nameTxtField);
        LocalDate eventDate = getLocalDate(datePicker);
        String eventStart = getTime(eventStartTxtfield);
        String info = txtAreaEmail.getText();
        String eventEnd = getTime(eventEndTxtfield);
        String loc = locationTxtfield.getText();

        if (eventName != null && eventDate != null && eventStart != null) {
            eventModel.createEvent(eventManager, eventName, eventDate, eventStart, info, eventEnd, loc);
            closeWindow(saveBtn);
        }
    }

    public void handleCancelBtn(ActionEvent actionEvent) {
        closeWindow(saveBtn);
    }

    @Override
    public void setPersonInfo(Person person) {
        eventManager = (EventManager) person;
    }


}
