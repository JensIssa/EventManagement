package dk.easv.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.sql.DataSource;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostOffice {
    private String getOutlook() {
        try {
            Process p = Runtime.getRuntime()
                    .exec(new String[]{"cmd.exe", "/c", "assoc", ".pst"});
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String extensionType = input.readLine();
            input.close();
            // extract type
            if (extensionType == null) {
                outlookNotFoundMessage("File type PST not associated with Outlook.");
            } else {
                String[] fileType = extensionType.split("=");

                p = Runtime.getRuntime().exec(
                        new String[]{"cmd.exe", "/c", "ftype", fileType[1]});
                input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String fileAssociation = input.readLine();
                // extract path
                Pattern pattern = Pattern.compile("\".*?\"");
                Matcher m = pattern.matcher(fileAssociation);
                if (m.find()) {
                    String outlookPath = m.group(0);
                    System.out.println(outlookPath);
                    return outlookPath;
                } else {
                    outlookNotFoundMessage("Error parsing PST file association");
                }
            }

        } catch (Exception err) {
            err.printStackTrace();
            outlookNotFoundMessage(err.getMessage());
        }
        return null;
    }

    private static void outlookNotFoundMessage(String errorMessage) {
        System.out.println("Could not find Outlook: \n" + errorMessage);
    }

    public void openOutlook() {
        String outlook = getOutlook();
        Runtime rt = Runtime.getRuntime();
        //C:\Users\deaso>"C:\Program Files\Microsoft Office\root\Office16\OUTLOOK.EXE" /m "cchesberg@gmail.com" /c ipm.note /a "c:\Users\deaso\random.dat

        try {
            String attachment = "src/Passivelapwing_9f0fdb_8839861.jpg";
            //String attachment = "resources/TempTickets/"+ticketSold.getTicketNumber()+".png";
            File file = new File(attachment);
            System.out.println(file.getAbsolutePath());
            rt.exec(new String[]{"cmd.exe", "/c", outlook, "/m", "vict657k@easv365.dk?subject=Ticket_Email", "/a", file.getAbsolutePath()});

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PostOffice postOffice = new PostOffice();
        postOffice.openOutlook();
    }
}
