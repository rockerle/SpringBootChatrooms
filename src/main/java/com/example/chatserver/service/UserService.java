package com.example.chatserver.service;

import com.example.chatserver.dto.MongoUser;
import com.example.chatserver.reps.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {
    private final IUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger("UsreService-Logger");

    @Autowired
    public UserService(IUserRepository repo, PasswordEncoder pwe){
        this.userRepo = repo;
        this.passwordEncoder = pwe;
    }

    public boolean addUser(MongoUser newUser){
        MongoUser u = userRepo.findByUsername(newUser.getUsername());
        if(u==null){
            u = new MongoUser();
            u.setId(String.valueOf(userRepo.findAll().size()));
            u.setUsername(newUser.getUsername());
            u.setPassword(passwordEncoder.encode(newUser.getPassword()));
            u.setRoles(List.of("USER"));
            u = userRepo.save(u);
            this.logger.info("added user to db: "+u.toString());
            return true;
        }else{
            this.logger.info("User already exists with the same name");
        }
        return false;
    }
}
