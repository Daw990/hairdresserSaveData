package com.dawid.hairdresserSaveData.services.implementation;

import com.dawid.hairdresserSaveData.entity.Visit;
import com.dawid.hairdresserSaveData.repository.VisitRepository;
import com.dawid.hairdresserSaveData.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class VisitServiceImpl implements VisitService {

    private final int MINUTES_SMALLEST_PART_OF_SERVICE = 20;
    private final LocalTime STARTING_TIME = LocalTime.of(8,0);
    private final int HOW_LONG_IS_OPEN_IN_MINS = 480;    // in minutes! 8 hours = 480 minutes
    /**
     * Customize a Time when salon should be open
     * STARTING_TIME = LocalTime.of(SET_YOUR_STARTING_HOUR, MINUTES);
     *
     * The smallest part of the service. for example, a man's haircut takes about 20 minutes
     * set this value to 20, then every next longest service will be multiplied by this value.
     * remember to change /resources/user/price-list-form.html validation input with: th:field="*{time}"
     * MINUTES_SMALLEST_PART_OF_SERVICE = 20;
     *
     * Type how long your local is open in minutes.
     * HOW_LONG_IS_OPEN_IN_MINS = 480
     * should be divisible by value of MINUTES_SMALLEST_PART_OF_SERVICE.
     *
     * **/

    private int howManyObjectsInListOfHours = HOW_LONG_IS_OPEN_IN_MINS/MINUTES_SMALLEST_PART_OF_SERVICE;

    VisitRepository visitRepository;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository){
        this.visitRepository = visitRepository;
    }

    @Override
    public List<Visit> findByIdUser(Long id) {

        return visitRepository.findByIdUser(id);
    }

    @Override
    public Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public List<Visit> findByVisitDate(LocalDate date) {
        return visitRepository.findByVisitDate(date);
    }

    @Override
    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }

    @Override
    public List<Visit> checkVisitsCanBeDeleteByUser(List<Visit> visits) {

        visits.forEach(visit -> {
            LocalDate localDate = visit.getVisitDate();
            visit.setCanDelete(localDate.isAfter(LocalDate.now()));
            visitRepository.save(visit);
        });
        return visits;
    }

    @Override
    public Map<LocalTime, Integer> fillHoursMap() {

        Map<LocalTime, Integer> hoursMap = new LinkedHashMap<>();
        long addMinutes = 0;

        for(int i = howManyObjectsInListOfHours; i > 0; i--){

            hoursMap.put(STARTING_TIME.plusMinutes(addMinutes), i);
            addMinutes+=MINUTES_SMALLEST_PART_OF_SERVICE;
        }
        return hoursMap;
    }

    @Override
    public Map<LocalTime, Integer> changeValuesInHoursMap(Map<LocalTime, Integer> hoursMap,
                                                          int repairTime, LocalTime startingHourRepair) {
        long addMinutes = 0;
        int timeOfServiceDivide = repairTime/MINUTES_SMALLEST_PART_OF_SERVICE;

        for(int i = 0; timeOfServiceDivide > i; i++){
            hoursMap.put(startingHourRepair.plusMinutes(addMinutes), 0);
            addMinutes+=MINUTES_SMALLEST_PART_OF_SERVICE;
        }

        hoursMap.forEach((k,v) -> {
            if(v == 0){
                LocalTime secondTime = k;

                int i = 1;
                int minutes = MINUTES_SMALLEST_PART_OF_SERVICE;

                if(hoursMap.get(secondTime) == 0) {

                    while ( true ) {
                        if(secondTime == STARTING_TIME) break;

                        secondTime = secondTime.minusMinutes(minutes);
                        if(hoursMap.get(secondTime) == 0) break;

                        hoursMap.put(secondTime, i);
                        i++;
                    }
                }
            }
        });
        return hoursMap;
    }

    @Override
    public List<LocalTime> getFreeHoursInList(Map<LocalTime, Integer> hoursMap, int serviceTime, LocalDate date) {

        int timeOfServiceDivide = serviceTime/MINUTES_SMALLEST_PART_OF_SERVICE;
        List<LocalTime> hoursList = new ArrayList<>();

        hoursMap.forEach((k,v) -> {
            if(date.equals(LocalDate.now())) {
                if(k.isAfter(LocalTime.now())) {
                    if (v >= timeOfServiceDivide) {
                        hoursList.add(k);
                    }
                }
            }else{
                if (v >= timeOfServiceDivide) {
                    hoursList.add(k);
                }
            }
        });
        return hoursList;
    }
}
