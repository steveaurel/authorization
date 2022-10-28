package com.spid.aruba.authorization;

import com.spid.aruba.authorization.model.AppRole;
import com.spid.aruba.authorization.model.AppUser;
import com.spid.aruba.authorization.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class AuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }

    /*
    * Utenti aggiunti per potere provare la login*/
    @Bean
    CommandLineRunner start(AppUserService userService) {
        return args -> {

            userService.saveUser(new AppUser(null, "Khephren", "K", "KMG1", "kk@gmail.com", "1234"));
            userService.saveUser(new AppUser(null, "Zahra", "K", "KMG2", "kz@gmail.com", "1234"));
            userService.saveUser(new AppUser(null, "Alvine", "K", "KMG3", "ka@gmail.com", "1234"));

            userService.changeRole(AppRole.USER, "kk@gmail.com");
            userService.changeRole(AppRole.ADMIN, "kz@gmail.com");
            userService.changeRole(AppRole.ADMIN, "ka@gmail.com");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
