package dk.easv.GUI.Controller;

import dk.easv.BE.Event;
import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.BE.User;
import dk.easv.GUI.Model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditEventsController extends SuperController implements Initializable, IEventController {
    @FXML
    private Button saveChanges;
    @FXML
    private DatePicker dateStart;
    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField timeStartTxtField;
    @FXML
    private TextField informationBoxTxtField;
    @FXML
    private TableView usersAtEventTable;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn emailColumn;

    private EventModel eventModel;

    private Event event = null;
    public EditEventsController() throws SQLException, IOException {
        eventModel = new EventModel();
   //     nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("Name"));
     //   emailColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("Email"));
       // usersAtEventTable.setItems(eventModel.getObservableUsersFromEvents(setEventInfo(event)));

    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addNumbersOnlyListener(timeStartTxtField);

    }

    @Override
    public Event setEventInfo(Event event) {
        this.event = event;
        nameTxtField.setText(event.getName());
        dateStart.setUserData(event.getStartDate());
        timeStartTxtField.setText(event.getStartTime());
      //  informationBoxTxtField.setText(event.getInfo());
        return event;
    }

    public void handleDeleteUserFromEvent(ActionEvent actionEvent) {
    }

    public void handleSaveChanges(ActionEvent actionEvent) {
        String name = getName(nameTxtField);
        LocalDate startDate = getLocalDate(dateStart);
        String timeStart = getTime(timeStartTxtField);
        String info = informationBoxTxtField.getText();

        if (name != null && startDate != null && timeStart != null)
        {
            int id = event.getId();
            Event event = new Event(id, name, startDate, timeStart, info);
            eventModel.updateEvent(event);
            closeWindow(saveChanges);
        }
    }
}
