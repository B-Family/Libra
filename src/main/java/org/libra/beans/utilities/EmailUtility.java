package org.libra.beans.utilities;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.libra.entities.implementation.ParticipantEntity;
import org.libra.entities.implementation.PresentationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringComponent
public class EmailUtility
{
    private final JavaMailSender javaMailSender;

    public void sendPutParticipantNotificationEmail(ParticipantEntity participantEntity)
    {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(participantEntity.getEmail());
        simpleMailMessage.setSubject("Your joined presentation");
        simpleMailMessage.setText("Your joined presentation with id: '" + participantEntity.getPresentationId() + "'");
        javaMailSender.send(simpleMailMessage);
    }
    public void sendDeletePresentationNotificationEmails(PresentationEntity presentationEntity)
    {
        for (ParticipantEntity participantEntity : presentationEntity.getParticipants())
        {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(participantEntity.getEmail());
            simpleMailMessage.setSubject("Presentation was canceled");
            simpleMailMessage.setText("Presentation with title: '" + presentationEntity.getTitle() + "' was canceled");
            javaMailSender.send(simpleMailMessage);
        }
    }

    @Autowired
    public EmailUtility(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }
}