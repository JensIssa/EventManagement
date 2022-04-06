package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;
import dk.easv.BE.Person;
import dk.easv.BE.User;
import dk.easv.BLL.PostOffice;
import dk.easv.GUI.Model.TicketModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MailPreviewController extends SuperController implements IEventController, Initializable {

    @FXML
    private Button closeBtn;
    @FXML
    private ScrollPane scrollPane;
    User user;
    Event event;
    TicketModel ticketModel;
    PostOffice postOffice = new PostOffice();

    public MailPreviewController() throws IOException {
        ticketModel = new TicketModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
    }

    public void handleClose(ActionEvent actionEvent) {
        closeWindow(closeBtn);
    }

    public void handleSendEmail(ActionEvent actionEvent) {
        String[] mailingList = {user.getEmail()};
        String subject = "Biletter til " + event.getName();
        String attachmentPath;
//TODO FIX - victor :))
        createAttachment();
    }

    private String createAttachment(){
        System.out.println("attachment click");
        File pdfFile = new File("biletter",String.valueOf(user.getId()+".png"));

            try {

                int width = (int)scrollPane.getWidth();
                int  height = (int) scrollPane.getHeight(); //TODO DEN SKAL IKKE VÆRE EN FAST STØRRELSE
                //Pad the capture area
                WritableImage writableImage = new WritableImage(width, height);
                scrollPane.contentProperty().get().snapshot(null,writableImage);
                scrollPane.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", pdfFile);
            } catch (IOException ex) { ex.printStackTrace(); }



        return null;
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
