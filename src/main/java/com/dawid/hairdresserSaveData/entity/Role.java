package com.dawid.hairdresserSaveData.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idRole;
    @Column
    private String role;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}