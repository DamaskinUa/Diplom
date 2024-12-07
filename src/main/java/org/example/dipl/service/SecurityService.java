package org.example.dipl.service;

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
    private SpringSecurityConfig SpringSecurityConfig;

    @Autowired(required = false)
    private ShiroConfig shiroConfig;

    /*@Autowired(required = false)
    private JAASConfig jaasConfig;*/

    public boolean authenticate(String username, String password) {
        System.out.println("Active Security Framework: " + activeSecurityFramework);
        switch (activeSecurityFramework) {
            case "spring-security":
                // Spring Security обробляє це автоматично
                return true;
            case "apache-shiro":
                return shiroConfig != null;
            case "jaas":
                System.setProperty("java.security.auth.login.config", "classpath:/jaas.config");
            default:
                throw new IllegalArgumentException("Unsupported security framework: " + activeSecurityFramework);
        }
    }
}

