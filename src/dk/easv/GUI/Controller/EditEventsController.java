package dk.easv.GUI.Controller;

import dk.easv.BE.*;
import dk.easv.GUI.Model.EventModel;
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

public class EditEventsController extends SuperController implements Initializable, IEventController{
    public TextField usersInEventsSearchTxt;
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

    @FXML
    private TableView<User> usersAtEventTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;

    private EventModel eventModel;
    private UserEventModel userEventModel;
    private EventManager eventManager;

    private Event event;
    public EditEventsController() throws SQLException, IOException {
        eventModel = new EventModel();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTimeListener(timeStartTxtField);
        maxLenghtListenerTxtArea(infoTxtArea);
        maxLenghtListener(nameTxtField, 60);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    @Override
    public void setEventInfo(Event event) throws IOException {
        userEventModel = new UserEventModel(event);
        this.event = event;
        nameTxtField.setText(event.getName());
        dateStart.setValue(event.getStartDate());
        timeStartTxtField.setText(event.getStartTime());
        infoTxtArea.setText(event.getInfo());
        usersAtEventTable.setItems(userEventModel.getObservableUsersFromEvents(event));
    }

    public void handleDeleteUserFromEvent(ActionEvent actionEvent) {
        User user = (User) usersAtEventTable.getSelectionModel().getSelectedItem();
        userEventModel.deleteUserFromEvent(user,event);
        usersAtEventTable.getItems().clear();
        usersAtEventTable.setItems(userEventModel.getObservableUsersFromEvents(event));
    }

    public void handleSaveChanges(ActionEvent actionEvent) {
        String name = getName(nameTxtField);
        LocalDate startDate = getLocalDate(dateStart);
        String timeStart = getTime(timeStartTxtField);
        String info = infoTxtArea.getText();

        if (name != null && startDate != null && timeStart != null)
        {
            int id = event.getId();
            Event event = new Event(id,eventManager.getId(), name, startDate, timeStart,eventManager.getName(), info);
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

    public void handleSearch(KeyEvent keyEvent) {
        String searchParam = usersInEventsSearchTxt.getText();
        ObservableList<User> foundUserList =  eventModel.searchUsersInEvents(usersAtEventTable.getItems(), searchParam, event);
        usersAtEventTable.setItems(foundUserList);
    }
}
