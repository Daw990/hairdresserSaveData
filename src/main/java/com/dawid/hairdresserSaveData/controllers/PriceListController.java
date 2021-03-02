package com.dawid.hairdresserSaveData.controllers;

import com.dawid.hairdresserSaveData.entity.PriceList;
import com.dawid.hairdresserSaveData.services.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class PriceListController {

    PriceListService priceListService;

    @Autowired
    public PriceListController(PriceListService priceListService){
        this.priceListService = priceListService;
    }

    @GetMapping(value= "/price_list_add")
    public String priceListAdd(Model model){

        model.addAttribute("addService", new PriceList());
        return "user/price-list-form";
    }

    @PostMapping(value = "/save_price_list")
    public String savePriceList(@ModelAttribute("newPriceList") PriceList priceList){

        priceListService.save(priceList);
        return "redirect:/price_list";
    }

    @GetMapping(value= "/price_list_edit")
    public String priceListEdit(@RequestParam("id") Long id, Model model){

        PriceList priceList = priceListService.findById(id);
        model.addAttribute("addService", priceList);
        return "user/price-list-form";
    }

    @GetMapping(value= "/price_list_delete")
    public String priceListDelete(@RequestParam("id") Long id){

        priceListService.deleteById(id);
        return "redirect:/price_list";
    }
}
