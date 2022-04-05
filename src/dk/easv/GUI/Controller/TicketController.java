package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;
import dk.easv.BE.Person;
import dk.easv.BE.User;
import dk.easv.BLL.EventBusinessManager;
import dk.easv.DAL.EventDAO;
import dk.easv.GUI.Model.EventModel;
import dk.easv.GUI.Model.TicketModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class TicketController implements IEventController {

    @FXML
    private Label startOfEventLabel;
    @FXML
    private Label locationOfEventLabel;
    @FXML
    private Label endOfEventLabel;
    @FXML
    private Label ticketIdLbl;
    @FXML
    private Label nameOfEventLabel;
    @FXML
    private Label dateOfEventLabel;

    private TicketModel ticketModel;
    User user;
    Event event;

    public TicketController() throws IOException {
        ticketModel = new TicketModel();
    }



    public void setTicketInfo() throws SQLServerException {
        nameOfEventLabel.setText(event.getName());
        locationOfEventLabel.setText(event.getLoc());
        ticketIdLbl.setText(String.valueOf(ticketModel.getTicketId()));
        dateOfEventLabel.setText(event.getStartDate().toString());
        startOfEventLabel.setText(event.getStartTime());
        endOfEventLabel.setText(event.getEndTime());
    }

    @Override
    public void setPersonInfo(Person person) {
        user = (User) person;
    }

    @Override
    public void setEventInfo(Event event) throws IOException, SQLServerException {
        this.event = event;
        setTicketInfo();
    }
}
