package dk.easv.BLL.Test;

import dk.easv.BLL.PostOffice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostOfficeTest {

    @DisplayName("Testing if one returns outlook")
    @Test

    public void testingOutlook(){
        String[] mailingList = {"mail@hotmailcom.dk", "mail2@hotmail.com"};
        String subject = "outlook";
        String attachmentPath = "something";
        PostOffice postOffice = new PostOffice();

        //Act
        postOffice.prepareOutlook(mailingList, subject, attachmentPath);



    }

}