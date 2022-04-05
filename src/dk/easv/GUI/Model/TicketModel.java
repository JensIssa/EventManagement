package dk.easv.GUI.Model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BLL.TicketManager;

import java.io.IOException;

public class TicketModel {
    private TicketManager ticketManager;

    public TicketModel() throws IOException {
        ticketManager = new TicketManager();
    }

    public int getTicketId() throws SQLServerException {
        return ticketManager.getTicketId();
    }
}
