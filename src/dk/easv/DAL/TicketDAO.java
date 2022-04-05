package dk.easv.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDAO {
    private DatabaseConnector dc;

    public TicketDAO() throws IOException {
        dc = new DatabaseConnector();
    }

    public int getTicketId() throws SQLServerException {
        int id = 0;
        try (Connection connection = dc.getConnection()) {
            String sqlStatement = "SELECT ID FROM Ticket WHERE ID =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int tempId = resultSet.getInt("ID");
                id = tempId;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
}
