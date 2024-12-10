import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class CertificateManager {
    private String certificateTemplate; //template for the certificate content
    private Date issueDate; //date when the certificate is issued
    private EmailService emailService; //handles email functionality

    //constructor initializes the template, issue date, and email service
    public CertificateManager(String certificateTemplate, EmailService emailService) {
        this.certificateTemplate = certificateTemplate;
        this.issueDate = new Date();
        this.emailService = emailService;
    }

    //generate and save certificate to a file
    public String generateAndSaveCertificate(Attendee attendee) {
        //replace placeholders in the template with attendee details
        String certificate = certificateTemplate
                .replace("{name}", attendee.getName())
                .replace("{date}", issueDate.toString());

        //save the certificate and return the file path
        return saveCertificateToFile(certificate, attendee.getAttendeeID());
    }

    //generate certificate content without saving
    public String generateCertificate(Attendee attendee) {
        return certificateTemplate
                .replace("{name}", attendee.getName())
                .replace("{date}", issueDate.toString());
    }

    //send the generated certificate to attendee via email
    public void sendCertificateByEmail(Attendee attendee) {
        //generate and save certificate
        String certificatePath = generateAndSaveCertificate(attendee);

        //email subject and body
        String subject = "Certificate for " + attendee.getName();
        String body = "Dear " + attendee.getName() + ",\n\n"
                + "Thank you for attending the conference. Please find your certificate attached.\n\n"
                + "Best regards,\nConference Team";

        //send the certificate using email service
        emailService.sendEmailWithAttachment(
                attendee.getEmail(),
                subject,
                body,
                certificatePath
        );
    }

    //save certificate to a file in the certificates directory
    private String saveCertificateToFile(String certificate, String attendeeID) {
        String directory = "certificates/";
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs(); //create the directory if it doesn't exist
        }

        String fileName = directory + "certificate_" + attendeeID + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(certificate); //write certificate content to the file
            System.out.println("Certificate saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving certificate for Attendee ID " + attendeeID + ": " + e.getMessage());
        }

        return fileName; //return the path of the saved file
    }

    //get the certificate template
    public String getCertificateTemplate() {
        return certificateTemplate;
    }

    //set a new certificate template
    public void setCertificateTemplate(String certificateTemplate) {
        this.certificateTemplate = certificateTemplate;
    }

    //get the issue date of the certificate
    public Date getIssueDate() {
        return issueDate;
    }

    //set a new issue date for the certificate
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}