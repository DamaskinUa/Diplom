package org.example.dipl;

import org.example.dipl.model.User;
import org.example.dipl.repo.UserRepository;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import java.io.IOException;
import java.util.Optional;

public class SimpleCallbackHandler implements CallbackHandler {

    private final String username;
    private final String password;

    public SimpleCallbackHandler(String username, String password) {
        this.username = username;
        this.password = password;
        System.out.println("passs "+password);
        System.out.println("passs "+this.password);
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                ((NameCallback) callback).setName(username);
            } else if (callback instanceof PasswordCallback) {
                ((PasswordCallback) callback).setPassword(password.toCharArray());
            }
        }
    }
}
