package dk.easv.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.AgeGroup;
import dk.easv.BE.Event;

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

    public int  countEventAttendees(Event event, AgeGroup ageGroup) throws SQLException {
        try (Connection connection = dc.getConnection()){
            String sql = "SELECT COUNT (age) AS amount\n" +
                    "FROM Ticket\n" +
                    "WHERE eventID = ? AND age = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, event.getId());
            preparedStatement.setInt(2, ageGroup.getI());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("amount");
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

}
