package org.example.dipl.controllers;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.example.dipl.form.RegistrationForm;
import org.example.dipl.model.RoleUser;
import org.example.dipl.model.User;
import org.example.dipl.repo.RoleUserRepository;
import org.example.dipl.repo.UserRepository;
import org.example.dipl.service.SecurityService;
import org.example.dipl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Arrays;

    @Controller
    public class UserController {
        @Autowired
        private Environment environment;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private SecurityService securityService;
        @Autowired
        private UserService userService;
        @Autowired
        private RoleUserRepository roleUserRepository;

        @GetMapping("/login")
        public String showLoginPage(Model model) {
            // Перевіряємо, який профіль активний: JAAS, Spring Security, або Apache Shiro
            boolean isJaasProfile = Arrays.asList(environment.getActiveProfiles()).contains("jaas");
            boolean isSpringSecurityProfile = Arrays.asList(environment.getActiveProfiles()).contains("spring-security");
            boolean isApacheShiroProfile = Arrays.asList(environment.getActiveProfiles()).contains("apache-shiro");

            // Передаємо інформацію у view
            model.addAttribute("isJaasProfile", isJaasProfile);
            model.addAttribute("isSpringSecurityProfile", isSpringSecurityProfile);
            model.addAttribute("isApacheShiroProfile", isApacheShiroProfile);
            return "login"; // Сторінка для входу
        }

        @PostMapping("/login")
        public String loginUser(@RequestParam String username, @RequestParam String password) {
            // Якщо активний профіль Apache Shiro
            boolean isApacheShiroProfile = Arrays.asList(environment.getActiveProfiles()).contains("apache-shiro");
            if (isApacheShiroProfile) {
                // Отримуємо об'єкт Subject з Shiro
                Subject currentUser = SecurityUtils.getSubject();

                // Спроба входу користувача
                try {
                    currentUser.login(new org.apache.shiro.authc.UsernamePasswordToken(username, password));
                    if (currentUser.isAuthenticated()) {
                        return "redirect:/profile"; // Перехід на сторінку профілю
                    }
                } catch (org.apache.shiro.authc.AuthenticationException e) {
                    return "redirect:/login?error=true"; // Помилка автентифікації
                }
            } else if (!isApacheShiroProfile) {

                // Якщо використовується інший профіль
                boolean isAuthenticated = securityService.authenticate(username, password);
                if (isAuthenticated) {
                    return "redirect:/profile";
                } else {
                    return "redirect:/login?error=true";
                }
            }
            return "redirect:/login?error=true";
        }

        @GetMapping("/profile")
        public String showProfilePage(Model model) {
            boolean isApacheShiroProfile = Arrays.asList(environment.getActiveProfiles()).contains("apache-shiro");
            if (isApacheShiroProfile) {
                // Отримуємо об'єкт Subject з Shiro
                Subject currentUser = SecurityUtils.getSubject();

                if (currentUser.isAuthenticated()) {
                    // Отримуємо ім'я користувача
                    String username = (String) currentUser.getPrincipal();

                    // Завантажуємо користувача з бази даних
                    User user = userRepository.findByLoginUser(username).orElse(null);
                    model.addAttribute("user", user);

                    return "profile"; // Відображення сторінки профілю
                } else {
                    return "redirect:/login"; // Перенаправлення на сторінку входу
                }
            }
            else {
                // Отримуємо аутентифікацію з SecurityContext
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                // Перевіряємо, чи користувач аутентифікований
                if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
                    // Отримуємо ім'я користувача
                    String username = authentication.getName();

                    // Завантажуємо користувача з бази даних
                    User user = userRepository.findByLoginUser(username).orElse(null);
                    model.addAttribute("user", user);

                    return "profile"; // Відображення сторінки профілю
                } else {
                    return "redirect:/login"; // Перенаправлення на сторінку входу
                }
            }
        }

        @PostMapping("/logout")
        public String logout() {
            // Якщо активний профіль Apache Shiro
            if (Arrays.asList(environment.getActiveProfiles()).contains("apache-shiro")) {
                Subject currentUser = SecurityUtils.getSubject();
                currentUser.logout(); // Виконуємо вихід через Shiro
            }

            return "redirect:/catalog"; // Перенаправлення на головну сторінку після виходу
        }

        @GetMapping("/profile/edit")
        public String showEditProfilePage(Model model) {
            // Отримуємо ім'я користувача з аутентифікації
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Завантажуємо користувача з бази даних
            User user = userRepository.findByLoginUser(username).orElse(null);

            if (user != null) {
                model.addAttribute("user", user);
                return "editProfile"; // Сторінка для редагування профілю
            } else {
                return "redirect:/profile"; // Якщо користувач не знайдений, перенаправляємо на профіль
            }
        }

        @PostMapping("/profile/edit")
        public String editProfile(@RequestParam String email, @RequestParam(required = false) String password) {
            // Отримуємо ім'я користувача з аутентифікації
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();

            // Викликаємо метод UserService для редагування користувача
            boolean success = userService.editUser(currentUsername, email, password);

            if (!success) {
                // Якщо зміни не були збережені (наприклад, через конфлікт email)
                return "redirect:/profile?error=emailExists";  // Перенаправляємо назад з повідомленням про помилку
            }

            return "redirect:/profile";  // Після успішного редагування перенаправляємо на профіль
        }
        @GetMapping("/register")
        public String showRegistrationPage(Model model) {
            model.addAttribute("registrationForm", new RegistrationForm());
            return "register"; // Сторінка реєстрації
        }

        @PostMapping("/register")
        public String registerUser(@ModelAttribute("registrationForm") RegistrationForm form, Model model) {
            // Перевірка, чи паролі співпадають
            if (!form.getPassword().equals(form.getConfirmPassword())) {
                model.addAttribute("errorMessage", "Паролі не співпадають!");
                return "register";
            }

            // Перевірка, чи логін вже існує
            if (userRepository.findByLoginUser(form.getUsername()).isPresent()) {
                model.addAttribute("errorMessage", "Користувач з таким логіном вже існує!");
                return "register";
            }

            // Перевірка, чи email вже використовується
            if (userRepository.findByEmail(form.getEmail()).isPresent()) {
                model.addAttribute("errorMessage", "Ця електронна адреса вже використовується!");
                return "register";
            }
            RoleUser role = roleUserRepository.findByNameRole("USER").orElse(null);

            // Реєстрація нового користувача
            userService.registerUser(
                    form.getUsername(),
                    form.getPassword(),
                    form.getEmail(),
                    role // Роль "USER" за замовчуванням
            );

            model.addAttribute("successMessage", "Реєстрація успішна! Ви можете увійти у свій акаунт.");
            return "register";
        }
    }


