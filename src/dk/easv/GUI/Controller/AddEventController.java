package dk.easv.GUI.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.Timestamp;

public class AddEventController {
    @FXML
    private TextField eventStartTxtfield;
    @FXML
    private TextField eventEndTxtfield;
    @FXML
    private TextField informationTxtfield;
    @FXML
    private TextField nameTxtField;

    public AddEventController(String name, Date start, Date end) {

    }
}
