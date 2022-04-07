package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Person;

public interface IController {
    /**
     * bruges til at sætte den person der er logget ind
     * @param person kan være alle underklasser af Person
     */
    abstract void setPersonInfo(Person person) throws SQLServerException;
}
