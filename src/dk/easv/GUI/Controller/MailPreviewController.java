package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;
import dk.easv.BE.Person;
import dk.easv.BE.User;
import dk.easv.GUI.Model.TicketModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class MailPreviewController extends SuperController implements IEventController {

    @FXML
    private Button closeBtn;
    @FXML
    private ScrollPane scrollPane;
    User user;
    Event event;
    TicketModel ticketModel;

    public MailPreviewController() throws IOException {
        ticketModel = new TicketModel();
    }

    public void handleClose(ActionEvent actionEvent) {
        closeWindow(closeBtn);
    }

    public void handleSendEmail(ActionEvent actionEvent) {
    }


    @Override
    public void setPersonInfo(Person person) throws SQLServerException {
        this.user = (User) person;
        ticketPreview();
    }

    @Override
    public void setEventInfo(Event event) throws IOException, SQLServerException {
        this.event = event;

    }

    public void ticketPreview() throws SQLServerException {
        List<Integer> listTicket = ticketModel.getTicketId(user);
        if (!listTicket.isEmpty()) {
            VBox tickets = new VBox(15);
            Label infoTextLabel = new Label(event.getInfo());
            infoTextLabel.setId("infoTextLabel");
            tickets.getChildren().add(infoTextLabel);
            for (Integer integer : listTicket) {
                try {
                    FXMLLoader root = new FXMLLoader(getClass().getResource("/dk/easv/GUI/View2/Ticket.fxml"));
                    Pane ticket = root.load();
                    ITicketAndEvent iTicketAndEvent = root.getController();
                    iTicketAndEvent.setEventAndTicketID(event, integer);
                    tickets.getChildren().add(ticket);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            scrollPane.setContent(tickets);

        }
    }
}
