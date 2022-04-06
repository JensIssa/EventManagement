package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;

public interface ITicketAndEvent {
    void setEventAndTicketID(Event event, int ticketId) throws SQLServerException;
}
