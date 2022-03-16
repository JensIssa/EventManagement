package dk.easv.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    private DatabaseConnector dc;

    public PersonDAO() throws IOException {
        dc = new DatabaseConnector();
    }


    /**
     * Generisk metode til at hente alle personer af den ønskede persontype
     * @param personType - Persontypen, hvori informationer ønskes hentet fra
     * @return - Liste af den persontype der er blevet valgt
     */
    private List<Person> getAllPerson(PersonType personType){
        ArrayList<Person> allPersons = new ArrayList<>();
        try (Connection connection = dc.getConnection()){
            String sqlStatement = "SELECT Person.ID, Person.email, Person.[name], Person.[password], Person.phoneNumber, [Role].[type]\n" +
                    "FROM Person\n" +
                    "INNER JOIN [Role] ON Person.roleID = [Role].ID WHERE roleID = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
           preparedStatement.setInt(1, personType.getI());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int id =resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    int phoneNumber = resultSet.getInt("phoneNumber");
                    switch (personType){
                        case USER ->
                            allPersons.add(new User(id, name,email, password, personType, phoneNumber));
                        case EVENTMANAGER ->
                            allPersons.add(new EventManager(id, name,email, password, personType));
                        case ADMIN ->
                            allPersons.add(new Admin(id, name,email, password, personType));
                }
            }
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
        return allPersons;
    }


    //Henter alle users
    public List<Person> getAllUsers(){
        return getAllPerson(PersonType.USER);
    }
    
    //Henter alle EventManagers
    public List<Person> getAllEventManagers(){
        return getAllPerson(PersonType.EVENTMANAGER);
    }

    //Henter alle Admins
    public List<Person> getAllAdmins(){
        return getAllPerson(PersonType.ADMIN);
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
        PersonDAO eventManagerDAO = new PersonDAO();

        System.out.println(eventManagerDAO.getAllAdmins());

    }

}
