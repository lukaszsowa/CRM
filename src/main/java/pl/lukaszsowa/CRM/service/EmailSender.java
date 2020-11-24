package pl.lukaszsowa.CRM.service;

public interface EmailSender {

    void sendEmail(String to, String subject, String content);

}
