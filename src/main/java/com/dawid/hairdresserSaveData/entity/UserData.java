package com.dawid.hairdresserSaveData.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idUserData;
    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private String phoneNumber;
    @Column
    private LocalDateTime dateRegistration;

    public UserData(String firstName, String secondName, String phoneNumber, LocalDateTime dateRegistration) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.dateRegistration = dateRegistration;
    }
}
