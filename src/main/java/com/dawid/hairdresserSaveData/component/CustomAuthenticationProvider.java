package com.dawid.hairdresserSaveData.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final String USERNAME_CANT_BE_NULL = "username can not be null";
    private static final String CREDENTIALS_CANT_BE_NULL = "credentials can not be null";
    private static final String INCORRECT_PASSWORD = "Incorrect Password";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        Object credentials = authentication.getCredentials();

        Assert.notNull(name, USERNAME_CANT_BE_NULL);
        Assert.notNull(credentials, CREDENTIALS_CANT_BE_NULL);

        if(credentials instanceof String){ //check credentials is string
            return null;
        }
        String password = credentials.toString(); //if credentials is String make it String

        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        boolean passwordCheck = passwordEncoder.matches(password, userDetails.getPassword()); //check password from site with db
        //boolean passwordCheck = userDetails.getPassword().equals(password);
        if(!passwordCheck){
            throw new BadCredentialsException(INCORRECT_PASSWORD);
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(name, password, userDetails.getAuthorities()); // return name, password, roles
        return token;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class); //UPAT why? this istance can get from site username: password: . not login by facebook like oauth
    }
}
