package com.wallet.cryptocurrency;

import com.wallet.cryptocurrency.domain.Role;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CryptocurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptocurrencyApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User admin = new User(1L, "Admin", "Admin", "admin", passwordEncoder.encode("admin123"), "admin@wp.pl", true, Role.ADMIN.toString());
            User user = new User(2L, "Maciej", "Smith", "maciej", passwordEncoder.encode("maciej123"), "maciej@wp.pl", true, Role.USER.toString());
            userRepository.save(admin);
            userRepository.save(user);
        };

    }

}
