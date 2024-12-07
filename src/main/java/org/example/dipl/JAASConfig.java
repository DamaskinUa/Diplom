package org.example.dipl;

import org.example.dipl.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

@Configuration
@Profile("jaas")
public class JAASConfig {

    private final UserRepository userRepository;
    @Autowired
    public JAASConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        MySQLLoginModule loginModule = new MySQLLoginModule(userRepository);

        // Продовжуємо процес аутентифікації через JAAS
        try {
            LoginContext loginContext = new LoginContext("MyJaasApp", new SimpleCallbackHandler(username, password));
            loginContext.login(); // Спроба входу
            return true;
        } catch (LoginException e) {
            e.printStackTrace();
            return false;
        }
    }
}

