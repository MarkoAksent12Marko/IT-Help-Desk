package com.example.ithelpdesk;

import com.example.ithelpdesk.model.AppUser;
import com.example.ithelpdesk.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ItHelpDeskApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                ItHelpDeskApplication.class,
                args
        );
    }

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository
                    .findByUsername("admin")
                    .isEmpty()) {

                AppUser admin = new AppUser();

                admin.setUsername("admin");

                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );

                admin.setRole("ADMIN");

                userRepository.save(admin);

                System.out.println(
                        "Admin account created."
                );
            }
        };
    }
}
