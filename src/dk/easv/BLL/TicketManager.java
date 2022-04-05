package dk.easv.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.DAL.TicketDAO;

import java.io.IOException;

public class TicketManager {
    TicketDAO ticketDAO;

    public TicketManager() throws IOException {
        ticketDAO = new TicketDAO();
    }

    public int getTicketId() throws SQLServerException {
        return ticketDAO.getTicketId();
    }
}
