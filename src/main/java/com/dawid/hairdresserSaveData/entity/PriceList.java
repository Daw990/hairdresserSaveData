package com.dawid.hairdresserSaveData.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPriceList;
    @Column
    private String name;
    @Column
    private Integer time;
    @Column
    private Integer price;
    @Column
    private String category;
}
