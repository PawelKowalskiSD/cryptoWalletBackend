package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.domain.Mail;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.VerifyToken;
import com.wallet.cryptocurrency.repository.UserRepository;
import com.wallet.cryptocurrency.repository.VerifyTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VerifyTokenService {
    private final VerifyTokenRepository verifyTokenRepository;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

    public void verifyToken(String verifyTokens) {
        VerifyToken verify = verifyTokenRepository.findByValue(verifyTokens);
        if (verify != null) {
            User user = verify.getUser();
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    public void createAndSaveToken(User user) {
        String verifyTokenValue = UUID.randomUUID().toString();
        VerifyToken verifyToken = new VerifyToken(verifyTokenValue, user);
        verifyTokenRepository.save(verifyToken);

        mailSenderService.sendMailAccountActivation(Mail.builder()
                        .mailTo(user.getMailAddressee())
                        .subject("Verify Token")
                        .message("We send you mail")
                        .toCc(null)
                        .build()
                , verifyToken
        );
    }
}
