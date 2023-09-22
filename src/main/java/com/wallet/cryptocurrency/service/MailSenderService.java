package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.domain.Mail;
import com.wallet.cryptocurrency.entity.VerifyToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailSenderService {
    private final JavaMailSender javaMailSender;
    private final MailCreatorService mailCreatorService;

    public void sendMailAccountActivation(final Mail mail, VerifyToken verifyToken) {
        log.info("Starting email preparation...");
        try {
            javaMailSender.send(createMessage(mail, verifyToken));
            log.info("Email has been sent");
        } catch (MailException e) {
            log.error("Failed to process email sending: " + e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMessage(Mail mail, VerifyToken verifyToken) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildVerifyEmail(mail.getMessage(), verifyToken), true);
        };
    }
}
