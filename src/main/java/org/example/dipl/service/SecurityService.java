package org.example.dipl.service;

import org.example.dipl.JAASConfig;
import org.example.dipl.ShiroConfig;
import org.example.dipl.SpringSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Value("${spring.profiles.active}")
    private String activeSecurityFramework;

    @Autowired(required = false)
    private SpringSecurityConfig springSecurityConfig;

    @Autowired(required = false)
    private ShiroConfig shiroConfig;

    @Autowired(required = false)
    private JAASConfig jaasConfig;


    /**
     * Метод для автентифікації користувача.
     */
    public boolean authenticate(String username, String password) {
        System.out.println("Active Security Framework: " + activeSecurityFramework);

        switch (activeSecurityFramework) {
            case "spring-security":
                return authenticateWithSpringSecurity(username, password);
            case "apache-shiro":
                return authenticateWithShiro(username, password);
            case "jaas":
                System.setProperty("java.security.auth.login.config", "classpath:/jaas.config");
                return authenticateWithJaas(username, password);
            default:
                throw new IllegalArgumentException("Unsupported security framework: " + activeSecurityFramework);
        }
    }

    private boolean authenticateWithSpringSecurity(String username, String password) {
        // Логіка автентифікації через Spring Security
        System.out.println("Authenticating with Spring Security...");
        return true; // Тут повинна бути інтеграція з `AuthenticationManager`.
    }

    private boolean authenticateWithShiro(String username, String password) {
        if (shiroConfig != null) {
            System.out.println("Authenticating with Shiro...");
            // Ваша логіка автентифікації з Shiro.
            return true;
        }
        return false;
    }

    private boolean authenticateWithJaas(String username, String password) {
        if (jaasConfig != null) {
            System.out.println("Authenticating with JAAS...");
            // Використовуємо JAASConfig для автентифікації
            jaasConfig.authenticate(username, password);
            return true;
        }
        return false;
    }
}


