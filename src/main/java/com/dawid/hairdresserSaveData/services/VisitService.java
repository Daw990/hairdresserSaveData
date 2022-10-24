package com.dawid.hairdresserSaveData.services;

import com.dawid.hairdresserSaveData.entity.Visit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface VisitService {

    List<Visit> findByIdUser(Long id);
    Visit save(Visit visit);
    List<Visit> findByVisitDate(LocalDate date);
    void deleteById(Long id);
    List<Visit> checkVisitsCanBeDeleteByUser(List<Visit> visits);

    Map<LocalTime, Integer> fillHoursMap();
    Map<LocalTime, Integer> changeValuesInHoursMap(Map<java.time.LocalTime, java.lang.Integer> hoursMap,
                                                   int repairTime, java.time.LocalTime startingHourRepair);
    List<LocalTime> getFreeHoursInList(Map<LocalTime, Integer> hoursMap, int repairTime, LocalDate date);

}
