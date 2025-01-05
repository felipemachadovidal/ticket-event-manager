package com.compassuol.ms_ticket_manager.testemailservice;

import com.compassuol.ms_ticket_manager.service.EmailNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EmailNotificationServiceTest {

    @InjectMocks
    private EmailNotificationService emailNotificationService;

    @Mock
    private JavaMailSender mailSender;

    @Test
    void testSendEmail() {
        String message = "Test email message";

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailNotificationService.sendEmail(message);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}