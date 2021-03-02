package com.dawid.hairdresserSaveData.services;

import com.dawid.hairdresserSaveData.entity.PriceList;

import java.util.List;

public interface PriceListService {

    PriceList findById(Long Id);
    PriceList save(PriceList priceList);
    void deleteById(Long id);
    List<PriceList> findByCategory(String category);


}
