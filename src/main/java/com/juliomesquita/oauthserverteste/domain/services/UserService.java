package com.juliomesquita.oauthserverteste.domain.services;

import com.juliomesquita.oauthserverteste.domain.entities.Provider;
import com.juliomesquita.oauthserverteste.domain.entities.User;
import com.juliomesquita.oauthserverteste.infrastructure.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    public UserService(UserRepository repository){
        this.repository = repository;
    }
    public void processOAuthPostLogin(String username) {
        User existUser = repository.getUserByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEnabled(true);

            repository.save(newUser);
        }

    }

}
