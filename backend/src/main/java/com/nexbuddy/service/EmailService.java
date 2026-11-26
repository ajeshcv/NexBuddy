package com.nexbuddy.service;

import com.nexbuddy.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final AppProperties appProperties;
    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    public EmailService(AppProperties appProperties, ObjectProvider<JavaMailSender> mailSenderProvider) {
        this.appProperties = appProperties;
        this.mailSenderProvider = mailSenderProvider;
    }

    public boolean isMailEnabled() {
        return appProperties.getMail().isEnabled();
    }

    public void sendOtpEmail(String toEmail, String otpCode, String subject, String bodyPrefix) {
        if (!appProperties.getMail().isEnabled()) {
            log.info("[DEV EMAIL] To: {} | Subject: {} | OTP: {}", toEmail, subject, otpCode);
            return;
        }

        JavaMailSender mailSender = mailSenderProvider != null ? mailSenderProvider.getIfAvailable() : null;
        if (mailSender == null) {
            log.warn("Mail is enabled but JavaMailSender bean is unavailable. OTP for {}: {}", toEmail, otpCode);
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(appProperties.getMail().getFrom());
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(bodyPrefix + "\n\nYour OTP code is: " + otpCode
                + "\nThis code expires in " + appProperties.getOtp().getExpirationMinutes() + " minutes.");
        mailSender.send(message);
    }
}
