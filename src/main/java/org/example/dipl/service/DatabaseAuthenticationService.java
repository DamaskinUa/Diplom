package org.example.dipl.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseAuthenticationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean authenticate(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";
        String storedPassword = jdbcTemplate.queryForObject(query, new Object[]{username}, String.class);

        if (storedPassword != null && storedPassword.equals(password)) {
            return true;
        }
        return false;
    }
}

