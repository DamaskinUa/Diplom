package org.example.dipl;

import org.example.dipl.model.User;
import org.example.dipl.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.security.auth.callback.CallbackHandler;
import java.util.Map;

public class MySQLLoginModule implements LoginModule {

    private String username;
    private String password;
    private final UserRepository userRepository;
    @Autowired
    public MySQLLoginModule(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(javax.security.auth.Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        // Ініціалізація
    }

    @Override
    public boolean login() throws LoginException {
        // Отримання імені та пароля
        // Потрібно зберігати ім'я користувача та пароль у змінних після виклику колбеків
        if (authenticateWithDatabase(username, password)) {
            return true;
        } else {
            throw new LoginException("Невірне ім'я користувача чи пароль");
        }
    }

    private boolean authenticateWithDatabase(String username, String password) {
        // Логіка аутентифікації з MySQL
        User user = userRepository.findByLoginUser(username).orElse(null);
        return user != null && user.getPasswordUser().equals(password);
    }

    @Override
    public boolean commit() throws LoginException {
        // Підтвердження аутентифікації
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        // Повернення в початковий стан
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        // Вихід з системи
        return true;
    }
}

