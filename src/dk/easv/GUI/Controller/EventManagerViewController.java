package dk.easv.GUI.Controller;

import dk.easv.BE.Person;
import javafx.event.ActionEvent;

import java.io.IOException;

 
public class EventManagerViewController extends SuperController  implements IController{
    @Override
    public void setPersonInfo(Person person) {

    }

    public void handleInspect(ActionEvent actionEvent) {
    }

    public void handleAddUserToEvent(ActionEvent actionEvent) {
    }

    public void handleAddEvent(ActionEvent actionEvent) {
    }

    public void handleAddGuest(ActionEvent actionEvent) throws IOException {
        openScene("dk/easv/GUI/View2/AddGuestView.fxml", true, "Add guest", true);
    }

    public void handleEditGuest(ActionEvent actionEvent) throws IOException {
        openScene("dk/easv/GUI/View2/EditGuestView.fxml", true, "Add guest", true);

    }


}
