package com.dawid.hairdresserSaveData.services.implementation;

import com.dawid.hairdresserSaveData.entity.PriceList;
import com.dawid.hairdresserSaveData.repository.PriceListRepository;
import com.dawid.hairdresserSaveData.services.PriceListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PriceListServiceImplTest {

    @Autowired
    PriceListService priceListService;
    @Autowired
    PriceListRepository priceListRepository;

    @BeforeEach
    void clearDatabase(){
        priceListRepository.deleteAll();
    }

    private PriceList getExampleCorrectPriceList(){
        PriceList priceList = new PriceList();
        priceList.setName("haircut");
        priceList.setCategory("Man");
        priceList.setTime(40);
        priceList.setPrice(30);
        return priceList;
    }

    @Test
    void shouldFindPriceListById() {
        //given
        PriceList priceList = getExampleCorrectPriceList();
        //when
        priceListService.save(priceList);
        //then
        PriceList load = priceListService.findById(priceList.getIdPriceList());
        assertThat(load).isEqualTo(priceList);
    }

    @Test
    void shouldDeletePriceListById(){
        //given
        PriceList priceList = getExampleCorrectPriceList();
        priceListService.save(priceList);
        //when
        priceListService.deleteById(priceList.getIdPriceList());
        //then
        Optional<PriceList> load = priceListRepository.findById(priceList.getIdPriceList());
        assertThat(load).isEmpty();
    }

    @Test
    void RuntimeExceptionShouldBeThrownIfPriceListIsNotPresent(){
        //given
        //when
        //then
        assertThrows(RuntimeException.class, () -> priceListService.findById(1L));
    }

    @Test
    void shouldSavePriceList(){
        //given
        PriceList priceList = getExampleCorrectPriceList();
        //when
        priceListService.save(priceList);
        //then
        PriceList load = priceListService.findById(priceList.getIdPriceList());
        assertAll(() -> {
            assertThat(priceList.getPrice()).isEqualTo(load.getPrice());
            assertThat(priceList.getPrice()).isEqualTo(load.getPrice());
            assertThat(priceList.getTime()).isEqualTo(load.getTime());
            assertThat(priceList.getCategory()).isEqualTo(load.getCategory());
        });
    }
    @Test
    void shouldFindPriceListByCategory(){
        //given
        PriceList priceList = getExampleCorrectPriceList();
        priceListService.save(priceList);
        //when
        List<PriceList> load = priceListService.findByCategory(priceList.getCategory());
        //then
        assertAll(() -> {
            assertThat(load).hasSize(1);
            assertThat(load).isNotEmpty();
            assertThat(load).containsAnyOf(priceList);
        });
    }

}