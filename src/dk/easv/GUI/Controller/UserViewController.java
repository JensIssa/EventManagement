package dk.easv.GUI.Controller;

import dk.easv.BE.Event;
import dk.easv.BE.Person;
import dk.easv.BE.User;
import dk.easv.GUI.Model.EventModel;
import dk.easv.GUI.Model.UserEventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserViewController extends SuperController implements IController, Initializable {
    @FXML
    private Button closeBtn;
    @FXML
    private ScrollPane scrollPain;
    User user;
    private EventModel eventModel;

    public UserViewController() throws IOException, SQLException {
        eventModel = new EventModel();
    }

    @Override
    public void setPersonInfo(Person person) {
        user = (User) person;
        ticketsToUser();
    }


    /*
     * step 1 liste af events, som user deltager i == DONE
     * step 2 loade eventsne i controlleren
     * step 3 set infoen
     */


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void ticketsToUser() {
        List<Event> listOfEvents = eventModel.getAllEventsFromUser(user);
        if (!listOfEvents.isEmpty()) {
            VBox tickets = new VBox(15);
            for (int i = 0; i < listOfEvents.size(); i++) {
                try {
                    FXMLLoader root = new FXMLLoader(getClass().getResource("/dk/easv/GUI/View2/Ticket.fxml"));
                    Pane ticket = root.load();

                    IEventController iEventController = root.getController();
                    iEventController.setEventInfo(listOfEvents.get(i));
                    iEventController.setPersonInfo(user);

                    tickets.getChildren().add(ticket);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            scrollPain.setContent(tickets);

        }
    }

    public void handleClose(ActionEvent actionEvent) throws IOException {
        closeWindow(closeBtn);
        openScene("/dk/easv/GUI/View/LoginView.fxml",false, "Loginscreen",false);
    }
}

