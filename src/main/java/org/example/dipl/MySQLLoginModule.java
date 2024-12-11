package org.example.dipl;

import jakarta.annotation.PostConstruct;
import org.example.dipl.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;

import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;
import javax.security.auth.Subject;


/**
 * MySQLLoginModule для JAAS.
 */
@Component
@EnableWebSecurity
public class MySQLLoginModule implements LoginModule {

    private String username;
    private String password;
    @Autowired
    private UserRepository userRepository;

    public MySQLLoginModule() {
        this.username = null;
        this.password = null;
        // Порожній конструктор
    }

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        try {
            // Перевірка наявності CallbackHandler
            if (callbackHandler == null) {
                throw new LoginException("No CallbackHandler available.");
            }
            NameCallback nameCallback = new NameCallback("Username: ");
            PasswordCallback passwordCallback = new PasswordCallback("Password: ", false);
            // Отримання даних через CallbackHandler
            try {
                callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});
            } catch (UnsupportedCallbackException e) {
                // Обробка виключення, якщо CallbackHandler не підтримує ці типи callback'ів
                throw new LoginException("Unsupported callback exception: " + e.getMessage());
            }

            // Отримання значень username і password
            username = nameCallback.getName();
            char[] passwordChars = passwordCallback.getPassword();

            if (passwordChars != null) {
                password = new String(passwordChars);
            } else {
                throw new LoginException("Password cannot be null.");
            }

        } catch (IOException e) {
            // Обробка помилок вводу/виводу
            System.err.println("Error during login process: " + e.getMessage());
            // Якщо сталася помилка, можна виконати певну додаткову обробку
        } catch (LoginException e) {
            // Логування та повторне викидання LoginException, якщо користувач не знайдений чи інша помилка
            System.err.println("LoginException: " + e.getMessage());
            // Перенаправлення або інші дії залежно від помилки
        }
    }

    @Override
    public boolean login() throws LoginException {
            if (userRepository.findByLoginUser(username)
                    .map(user -> user.getPasswordUser().equals(password))
                    .orElse(false)) {
                return true;
            } else {
                throw new LoginException("Invalid credentials.");
            }
    }

    private boolean authenticateWithDatabase(String username, String password) {
        if (userRepository == null) {
            throw new IllegalStateException("UserRepository is not initialized.");
        }

        return userRepository.findByLoginUser(username)
                .map(user -> password.equals(user.getPasswordUser()))
                .orElse(false);
    }

    @Override
    public boolean commit() throws LoginException {
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return true;
    }
}


