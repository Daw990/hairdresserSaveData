package com.dawid.hairdresserSaveData.repository;

import com.dawid.hairdresserSaveData.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("select i from Visit i where i.visitDate=:date")
    List<Visit> findByVisitDate(@Param("date") LocalDate date);

    @Query("select i from Visit i where i.user.idUser=:idUser")
    List<Visit> findByIdUser(@Param("idUser") Long idUser);
}
