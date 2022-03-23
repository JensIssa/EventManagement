package dk.easv.GUI.Controller;

import dk.easv.BE.Event;
import dk.easv.BE.Person;
import dk.easv.BE.User;
import dk.easv.BLL.EventBusinessManager;
import dk.easv.DAL.EventDAO;
import dk.easv.GUI.Model.EventModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class TicketController implements IEventController{

    @FXML
    private Label nameOfEventLabel;
    @FXML
    private Label infoOfEventLabel;
    @FXML
    private Label dateOfEventLabel;
    @FXML
    private Label timeOfEventLabel;
    EventDAO eventDAO;
    EventBusinessManager eventBusinessManager;
    EventModel eventModel;
    User user;
    Event event;

    public TicketController() {

    }

    public void setTicketInfo(){
        nameOfEventLabel.setText(event.getName());
        infoOfEventLabel.setText(event.getInfo());
        dateOfEventLabel.setText(event.getStartDate().toString());
        timeOfEventLabel.setText(event.getStartTime());
    }

    @Override
    public void setPersonInfo(Person person) {
        user = (User)person;
    }

    @Override
    public void setEventInfo(Event event) throws IOException {
        this.event = event;
        setTicketInfo();
    }
}
