package com.dawid.hairdresserSaveData.services.implementation;

import com.dawid.hairdresserSaveData.component.mailBuilder.SignUpMail;
import com.dawid.hairdresserSaveData.component.mailBuilder.StringFactory;
import com.dawid.hairdresserSaveData.entity.Role;
import com.dawid.hairdresserSaveData.entity.User;
import com.dawid.hairdresserSaveData.entity.UserData;
import com.dawid.hairdresserSaveData.repository.RoleRepository;
import com.dawid.hairdresserSaveData.repository.UserDataRepository;
import com.dawid.hairdresserSaveData.repository.UserRepository;
import com.dawid.hairdresserSaveData.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

@Component
public class SignUpServiceImpl implements SignUpService, SecurityContext {

    private static final int TOKEN_LENGTH = 20;

    private UserRepository userRepository;
    private UserDataRepository userDataRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private SignUpMail signUpMail;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, UserDataRepository userDataRepository,
                             PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                             SignUpMail signUpMail) {

        this.userRepository = userRepository;
        this.userDataRepository = userDataRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.signUpMail = signUpMail;
    }

    @Override
    public Authentication getAuthentication() {
        return null;
    }

    @Override
    public void setAuthentication(Authentication authentication) {

    }

    @Override
    public void signUpUser(User user, UserData userData) {

        Assert.isNull(user.getIdUser(), "Can't sign up given user, it already has set id. User: " + user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> findRole = roleRepository.findByRole("USER");
        //jezeli udasie pobrac role to bedziemy ja przypisaywac
        findRole.ifPresent(role -> user.getRoles().add(role));

        String token = StringFactory.getRandomString(TOKEN_LENGTH);

        user.setConfirmationToken(token);
        signUpMail.sendConfirmationLink(user.getEmail(), token);
        user.setUserData(userData);
        userRepository.save(user);
        userDataRepository.save(userData);

    }

    @Override
    public User findById(Long id) {

        Optional<User> result = userRepository.findById(id);

        User user = null;

        if (result.isPresent()) {
            user = result.get();
        }
        else {
            throw new RuntimeException("Did not find user id - " + id);
        }
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUserData(UserData userData) {
        return userRepository.findByUserData(userData);
    }
}
