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

public class TicketController implements ITicketAndEvent {

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

    Event event;

    public TicketController() throws IOException {
    }



    public void setTicketInfo(int ticketId) throws SQLServerException {
        nameOfEventLabel.setText(event.getName());
        locationOfEventLabel.setText(event.getLoc());
        ticketIdLbl.setText(String.valueOf(ticketId));
        dateOfEventLabel.setText(event.getStartDate().toString());
        startOfEventLabel.setText(event.getStartTime());
        endOfEventLabel.setText(event.getEndTime());
    }


    @Override
    public void setEventAndTicketID(Event event, int ticketId) throws SQLServerException {
        this.event = event;
        setTicketInfo(ticketId);
    }
}
