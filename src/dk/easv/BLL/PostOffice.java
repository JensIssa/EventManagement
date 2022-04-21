package dk.easv.BLL;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostOffice {
    /**
     * finder hvor på computeren outlook er installeret
     *
     * @return pathen til outlook.exe
     */
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

    /**
     * bygger en sammenhængede string af emails speraret af ;
     *
     * @param mailingList arrayliste af emails
     * @return string af emails speraret af ;
     */
    private String buildMailString(String[] mailingList) {
        StringBuilder sb = new StringBuilder();
        for (String s : mailingList) {
            sb.append(s.trim()).append(";");
        }
        return sb.toString();
    }

    /**
     * fjerner ulovlige tegn i subject inputtet
     *
     * @param rawSubject subject til email
     * @return en string der er encoded til at ikke indeholde ulovlige tegn til en mails
     */
    private String buildSubjectString(String rawSubject) {
        return rawSubject.trim()
                .replace(" ", "%20");
    }


    /**
     * åbner outlook og auto udfylder de relevante info
     *
     * @param mailingList    arrayliste af email adresser der skal modtage emailen
     * @param subject        Emnet til emailen
     * @param attachmentPath pathen til den valgte attachment
     */
    public void prepareOutlook(String[] mailingList, String subject, String attachmentPath) {
        String outlook = getOutlook();
        Runtime rt = Runtime.getRuntime();

        String subjectString = buildSubjectString(subject);
        String mailString = buildMailString(mailingList);
        try {
            File file = new File(attachmentPath);
            System.out.println(file.getAbsolutePath());
            rt.exec(new String[]{"cmd.exe", "/c", outlook, "/m", mailString + "?subject=" + subjectString, "/a", file.getAbsolutePath()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
