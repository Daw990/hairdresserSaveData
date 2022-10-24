package com.dawid.hairdresserSaveData;

import com.dawid.hairdresserSaveData.component.CustomAuthenticationProvider;
import com.dawid.hairdresserSaveData.services.implementation.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug=false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired//nie mozna wstrzyknac przez konstruktor bo jest juz w CustomAuthenticationProvider
    CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception{
        authentication.userDetailsService(userDetailsServiceImpl);
        authentication.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/js/**", "/font/**").permitAll()
                .antMatchers("/", "/webStatic/**", "/login","/sign_up"
                        ,"/price_list","/confirm_email","/contact","/h2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()//my login page
                .loginPage("/login")
                .defaultSuccessUrl("/user/user_panel", true)
                .and()
                .logout()
                .logoutSuccessUrl("/?logout")
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }

    // @Bean
    // public PasswordEncoder passwordEncoder(){
    //     return new BCryptPasswordEncoder();
    // }


   @Bean
   public static NoOpPasswordEncoder passwordEncoder(){
       return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
   }
}
