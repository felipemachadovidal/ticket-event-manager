package com.compassuol.ms_ticket_manager.service;
import com.compassuol.ms_ticket_manager.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {
    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void sendEmail(String message) {
        System.out.println("Processing email notification...");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("seu-email@gmail.com"); // Certifique-se de usar o mesmo e-mail configurado
        mailMessage.setTo("destinatario@example.com"); // Substitua pelo destinatário
        mailMessage.setSubject("Ticket Confirmation");
        mailMessage.setText(message);

        try {
            mailSender.send(mailMessage);
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
