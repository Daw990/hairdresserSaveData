package com.dawid.hairdresserSaveData.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idUser;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String confirmationToken;
    @Column(columnDefinition = "boolean default false")
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private UserData userData;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="user_roles", joinColumns = @JoinColumn(name="id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> roles;

    public User(String email, String password) {
        super();
        this.email = email;
        this.password = password;
        this.enabled = false;
    }

    // User Builder
    public static User of(String email, String password){

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(false);
        user.roles = new ArrayList<Role>();

        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getRole())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
