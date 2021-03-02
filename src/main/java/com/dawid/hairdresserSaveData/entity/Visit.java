package com.dawid.hairdresserSaveData.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVisit;
    @Column
    private LocalDateTime visitStartedDate;
    @Column
    private LocalDate visitDate;
    @Column
    private LocalTime visitTime;
    @Column
    private boolean canDelete;
    @Column
    private String description;
    @Column
    private String serviceName;
    @Column
    private Integer serviceTime;
    @Column
    private Integer serviceCost;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}  )
    @JoinColumn(name="id_user")
    private User user;

    public Visit (LocalDateTime visitStartedDate, LocalDate visitDate, LocalTime visitTime, User user, PriceList priceList){
        this.visitStartedDate = visitStartedDate;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.user = user;
    }

    public Visit (LocalDateTime visitStartedDate, LocalDate visitDate, User user, PriceList priceList){
        this.visitStartedDate = visitStartedDate;
        this.visitDate = visitDate;
        this.user = user;
    }
}
