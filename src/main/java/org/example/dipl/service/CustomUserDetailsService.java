package org.example.dipl.service;

import org.example.dipl.model.User;
import org.example.dipl.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByLoginUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        // Extract the role and add the "ROLE_" prefix
        String roleName = user.getRole().getNameRole();  // Get role name from the RoleUser entity
        String roleWithPrefix = "ROLE_" + roleName.toUpperCase();  // Add ROLE_ prefix and ensure it's uppercase

        // Return the user with the authorities
        return new org.springframework.security.core.userdetails.User(
                user.getLoginUser(),
                user.getPasswordUser(),
                AuthorityUtils.createAuthorityList(roleWithPrefix)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRole() == null ? Collections.emptyList() :
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getNameRole()));
    }
}


