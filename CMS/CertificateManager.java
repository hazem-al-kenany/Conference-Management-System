import java.util.Date;

public class CertificateManager {
    private String certificateTemplate;
    private Date issueDate;

    public CertificateManager(String certificateTemplate) {
        this.certificateTemplate = certificateTemplate;
        this.issueDate = new Date();
    }

    //generate a certificate for an attendee
    public String generateCertificate(Attendee attendee) {
        //will still replace placeholders in the template with attendee-specific data
        String certificate = certificateTemplate
                .replace("{name}", attendee.getName())
                .replace("{date}", issueDate.toString());
        System.out.println("Certificate generated for: " + attendee.getName());
        System.out.println(certificate);
        return certificate;
    }

    //simulate sending the certificate by email
    public void sendCertificateByEmail(Attendee attendee) {
        String certificate = generateCertificate(attendee); //generate certificate
        System.out.println("Sending certificate to: " + attendee.getEmail());
        System.out.println("Email body: \n" + certificate);
        //find a way to integrate email here if possible
    }

    //getters & setters
    public String getCertificateTemplate() {
        return certificateTemplate;
    }

    public void setCertificateTemplate(String certificateTemplate) {
        this.certificateTemplate = certificateTemplate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}
