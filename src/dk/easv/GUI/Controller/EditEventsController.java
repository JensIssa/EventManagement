package dk.easv.GUI.Controller;

import dk.easv.BE.*;
import dk.easv.GUI.Model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditEventsController extends SuperController implements Initializable, IEventController {
    @FXML
    private TextField timeEndTxtField;
    @FXML
    private TextField locationTxtField;
    @FXML
    private TextArea infoTxtArea;
    @FXML
    private Button closeBtn;
    @FXML
    private Button saveChanges;
    @FXML
    private DatePicker dateStart;
    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField timeStartTxtField;

    private final EventModel eventModel;

    private EventManager eventManager;
    private Event event;

    public EditEventsController() throws SQLException, IOException {
        eventModel = new EventModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTimeListener(timeStartTxtField);
        maxLengthListenerTxtArea(infoTxtArea);
        maxLengthListener(nameTxtField, 60);
        maxLengthListener(locationTxtField, 100);
        infoTxtArea.setWrapText(true);
    }

    @Override
    public void setEventInfo(Event event) throws IOException {
        this.event = event;
        nameTxtField.setText(event.getName());
        dateStart.setValue(event.getStartDate());
        timeStartTxtField.setText(event.getStartTime());
        timeEndTxtField.setText(event.getEndTime());
        infoTxtArea.setText(event.getInfo());
        locationTxtField.setText(event.getLoc());
    }

    public void handleSaveChanges(ActionEvent actionEvent) {
        String name = getName(nameTxtField);
        LocalDate startDate = getLocalDate(dateStart);
        String timeStart = getTime(timeStartTxtField);
        String info = infoTxtArea.getText();
        String endTime = getTime(timeEndTxtField);
        String loc = locationTxtField.getText();

        if (name != null && startDate != null && timeStart != null) {
            int id = event.getId();
            Event event = new Event(id, eventManager.getId(), name, startDate, timeStart, eventManager.getName(), info, endTime, loc);
            eventModel.updateEvent(event);
            closeWindow(saveChanges);
        }
    }

    @Override
    public void setPersonInfo(Person person) {
        this.eventManager = (EventManager) person;
    }

    public void handleClose(ActionEvent actionEvent) {
        closeWindow(closeBtn);
    }
}
