package dk.easv.DAL;

import dk.easv.BE.PersonType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventManagerDAO {
    private DatabaseConnector dc;

    public EventManagerDAO() throws IOException {
        dc = new DatabaseConnector();
    }

    public void createPerson(String name, String email, String password, PersonType usertype) {
        try (Connection con = dc.getConnection()) {
            String sql = "INSERT INTO Person(Email,Password,roleID,Name) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setInt(3, usertype.getI());
            ps.setString(4, name);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void createPerson(String name, String email, String password, PersonType usertype,int phoneNumber) {
        try (Connection con = dc.getConnection()) {
            String sql = "INSERT INTO Person(Email,Password,roleID,phoneNumber,Name) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setInt(3, usertype.getI());
            ps.setInt(4,phoneNumber);
            ps.setString(5, name);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //TODO FIX
    public PersonType getPersonType(String email, String password) {
        if (loginUser(email, password)) {
            try (Connection con = dc.getConnection()) {
                String sql =
                        "Select UserType " +
                                "from [User]" +
                                "where Email = ? and Password = ?";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String type = rs.getString("UserType");
                    return PersonType.valueOf(type);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public boolean loginUser(String email, String password) {
        try (Connection con = dc.getConnection()) {
            String sql =
                    "Select UserType " +
                            "from [User]" +
                            "where Email = ? and Password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        EventManagerDAO eventManagerDAO = new EventManagerDAO();

        eventManagerDAO.createPerson("userman","usermail","password",PersonType.USER,1234567);

    }

}
