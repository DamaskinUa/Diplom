package org.example.dipl;

import org.example.dipl.model.User;
import org.example.dipl.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("jaas")
public class JAASConfig {
    @Autowired
    private UserRepository userRepository;



    public boolean authenticate(String username, String password) {
        // Продовжуємо процес автентифікації через JAAS
        try {
            LoginContext context = new LoginContext("MyJaasApp", new SimpleCallbackHandler(username, password));
            context.login();
            return true;
        } catch (LoginException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean authorize(String username, String role) {
        // Знайти користувача за логіном
        User user = userRepository.findByLoginUser(username).orElse(null);
        // Перевірка, чи користувач існує і чи його роль збігається з очікуваною
        return user != null && user.getRole() != null && role.equals(user.getRole().getNameRole());
    }
}

