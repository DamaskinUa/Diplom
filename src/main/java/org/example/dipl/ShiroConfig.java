package org.example.dipl;

import jakarta.annotation.PostConstruct;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Profile("apache-shiro")
public class ShiroConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/dipl");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public JdbcRealm jdbcRealm(DataSource dataSource) {
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);

        // SQL-запити для аутентифікації та авторизації
        jdbcRealm.setAuthenticationQuery("SELECT password_user FROM user_data WHERE login_user = ?");
        jdbcRealm.setUserRolesQuery("SELECT name_role FROM role_user r JOIN user_data u ON u.role_id = r.id_role WHERE u.login_user = ?");
        jdbcRealm.setPermissionsQuery("SELECT permission FROM permissions WHERE role_name = ?");

        jdbcRealm.setPermissionsLookupEnabled(true);
        return jdbcRealm;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(JdbcRealm jdbcRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(jdbcRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // Налаштування фільтрів доступу
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/catalog/**", "anon");
        filterChainDefinitionMap.put("/titles/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");

        // Protected resources
        filterChainDefinitionMap.put("/profile", "authc, roles[USER, ADMIN]");
        filterChainDefinitionMap.put("/admin/**", "authc, roles[ADMIN]");

        // Default to authentication required
        filterChainDefinitionMap.put("/**", "authc");

        // Login and logout filters
        filterChainDefinitionMap.put("/logout", "logout");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @PostConstruct
    public void init() {
        // Встановлюємо SecurityManager в ThreadContext після того, як всі біни ініціалізовані
        SecurityUtils.setSecurityManager(securityManager(jdbcRealm(dataSource())));
    }
}
