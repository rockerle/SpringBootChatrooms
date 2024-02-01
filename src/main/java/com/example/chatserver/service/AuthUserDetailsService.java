package com.example.chatserver.service;

import com.example.chatserver.dto.MongoUser;
import com.example.chatserver.dto.AuthUser;
import com.example.chatserver.reps.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AuthUserDetailsService implements UserDetailsService, UserDetailsPasswordService {

    private final Logger logger = Logger.getLogger("AUDS");
    private final IUserRepository userRepository;
    private final PasswordEncoder pwEncoder;

    @Autowired
    public AuthUserDetailsService(IUserRepository repo, PasswordEncoder pwEncoder){
        this.userRepository = repo;
        this.pwEncoder=pwEncoder;
    }
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        MongoUser u = userRepository.findByUsername(user.getUsername());
        u.setPassword(newPassword);
        userRepository.save(u);
        return new AuthUser(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MongoUser user = userRepository.findByUsername(username);
        if(user==null)
            throw new UsernameNotFoundException("User not found");
        return new AuthUser(user);
    }

    public boolean addUser(MongoUser newUser){
        MongoUser u = userRepository.findByUsername(newUser.getUsername());
        if(u==null){
            u = new MongoUser();
            u.setId(String.valueOf(userRepository.findAll().size()));
            u.setUsername(newUser.getUsername());
            u.setPassword(pwEncoder.encode(newUser.getPassword()));
            u.setRoles(List.of("USER"));
            u = userRepository.save(u);
            this.logger.info("added user to db: "+u.toString());
            return true;
        }else{
            this.logger.info("User already exists with the same name");
        }
        return false;
    }
}
