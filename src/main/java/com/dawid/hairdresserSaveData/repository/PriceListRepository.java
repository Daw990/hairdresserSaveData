package com.dawid.hairdresserSaveData.repository;

import com.dawid.hairdresserSaveData.entity.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    List<PriceList> findByCategory(String category);
}
