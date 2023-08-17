package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.config.ConfigApp;
import com.wallet.cryptocurrency.entity.VerifyToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
@Service
public class MailCreatorService {

    private final TemplateEngine templateEngine;
    private final ConfigApp configApp;

    public String buildVerifyEmail(String message, VerifyToken verifyToken) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("config", configApp);
        context.setVariable("url", "auth/verify?token=");
        context.setVariable("verifyToken", verifyToken.getValue());
        return templateEngine.process("verify-token-mail.html", context);
    }

}
