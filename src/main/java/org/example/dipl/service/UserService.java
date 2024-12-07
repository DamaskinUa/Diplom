package org.example.dipl.service;

import org.example.dipl.model.RoleUser;
import org.example.dipl.model.User;
import org.example.dipl.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        System.out.println("passwordEncoder " + passwordEncoder);
    }

    public void registerUser(String login, String password, String email, RoleUser role) {
        String encodedPassword = passwordEncoder.encode(password);  // Хешуємо пароль
        User user = new User();
        user.setLoginUser(login);
        user.setPasswordUser(encodedPassword);
        user.setEmail(email);  // Встановлюємо email
        user.setDataRegistri(LocalDate.now());  // Встановлюємо дату реєстрації
        user.setRole(role);  // Встановлюємо роль
        userRepository.save(user);
    }
    // Метод для редагування профілю користувача
    public boolean editUser(String currentUsername, String newEmail, String newPassword) {
        // Завантажуємо користувача за його логіном
        User existingUser = userRepository.findByLoginUser(currentUsername).orElse(null);
        if (existingUser != null) {
            // Перевіряємо, чи існує вже користувач з таким email
            Optional<User> userWithSameEmail = userRepository.findByEmail(newEmail);
            if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getLoginUser().equals(currentUsername)) {
                // Якщо email вже використовується іншим користувачем
                return false;
            }

            // Оновлюємо email користувача
            existingUser.setEmail(newEmail);

            // Якщо новий пароль не порожній, хешуємо його і оновлюємо
            if (newPassword != null && !newPassword.isEmpty()) {
                String encodedPassword = passwordEncoder.encode(newPassword);
                existingUser.setPasswordUser(encodedPassword);
            }

            // Зберігаємо зміни в базі даних
            userRepository.save(existingUser);
            return true;
        }

        // Якщо користувач не знайдений
        return false;
    }
}

