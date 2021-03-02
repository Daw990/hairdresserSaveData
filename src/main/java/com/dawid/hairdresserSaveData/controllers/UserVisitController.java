package com.dawid.hairdresserSaveData.controllers;

import com.dawid.hairdresserSaveData.custom.DateAndTime;
import com.dawid.hairdresserSaveData.entity.PriceList;
import com.dawid.hairdresserSaveData.entity.User;
import com.dawid.hairdresserSaveData.entity.UserData;
import com.dawid.hairdresserSaveData.entity.Visit;
import com.dawid.hairdresserSaveData.services.PriceListService;
import com.dawid.hairdresserSaveData.services.SignUpService;
import com.dawid.hairdresserSaveData.services.UserDataService;
import com.dawid.hairdresserSaveData.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserVisitController {

    PriceListService priceListService;
    UserDetailsService userDetailsService;
    VisitService visitService;
    UserDataService userDataService;
    SignUpService signUpService;

    @Autowired
    public UserVisitController(PriceListService priceListService, UserDetailsService userDetailsService,
                               VisitService visitService, UserDataService userDataService,
                               SignUpService signUpService) {
        this.userDetailsService = userDetailsService;
        this.priceListService = priceListService;
        this.visitService = visitService;
        this.userDataService = userDataService;
        this.signUpService = signUpService;
    }

    @GetMapping(value = "/make_visit")
    public String makeVisit(@RequestParam("idPriceList") Long id, Model model) {

        PriceList priceList = priceListService.findById(id);
        model.addAttribute("priceList", priceList);
        return "user/make-visit";
    }

    @GetMapping(value = "/make_visit_date")
    public String makeVisitDate(@RequestParam("idPriceList") Long id,
                                @RequestParam(name = "date", required = false, defaultValue = "2021-01-01") String dateVisit,
                                Model model, Visit visit) {

        LocalDate dateVisit2 = LocalDate.parse(dateVisit);
        PriceList priceList = priceListService.findById(id);
        List<Visit> visitList = visitService.findByVisitDate(dateVisit2);
        Map<LocalTime, Integer> hoursMap = visitService.fillHoursMap();
        List<LocalTime> hours = visitService.getFreeHoursInList(hoursMap, priceList.getTime(), dateVisit2);

        if (!visitList.isEmpty()) {

            for (Visit visit2 : visitList) {

                LocalTime visitTime = visit2.getVisitTime();
                hoursMap = visitService.changeValuesInHoursMap(hoursMap, visit2.getServiceTime(), visitTime);
            }

            hours = visitService.getFreeHoursInList(hoursMap, priceList.getTime(), dateVisit2);
            model.addAttribute("hours", hours);
        } else {

            model.addAttribute("hours", hours);
        }
        model.addAttribute("visit", visit);
        model.addAttribute("priceList", priceList);
        model.addAttribute("dateVisit", dateVisit);
        return "user/make-visit";
    }

    @PostMapping(value = "/save_visit")
    public String addVisit(@RequestParam("idPriceList") Long id,
                           @RequestParam(value = "date", required = false) String date,
                           @RequestParam("newClient") String newClient,
                           @ModelAttribute("visit") Visit visit,
                           @RequestParam(value = "firstName", required = false) String firstName,
                           @RequestParam(value = "secondName", required = false) String secondName,
                           @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                           @RequestParam(value = "description", required = false) String description,
                           Authentication authentication) {

        PriceList priceList = priceListService.findById(id);
        LocalDate localDate = LocalDate.parse(date);
        visit.setServiceName(priceList.getName());
        visit.setVisitDate(localDate);
        visit.setVisitStartedDate(DateAndTime.getDateAndTime());
        visit.setDescription(description);
        visit.setServiceTime(priceList.getTime());
        visit.setServiceCost(priceList.getPrice());

        if (localDate.isBefore(LocalDate.now())) {
            visit.setCanDelete(false);
        } else {
            visit.setCanDelete(true);
        }

        if (newClient.equals("true")) {

            UserData userData = new UserData(firstName, secondName, phoneNumber, DateAndTime.getDateAndTime());
            User user = new User();
            user.setUserData(userData);
            signUpService.save(user);
            userDataService.save(userData);

            visit.setUser(user);
        } else {
            User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
            visit.setUser(user);
        }
        visitService.save(visit);

        return "redirect:/user/user_panel";
    }

    @GetMapping(value = "/my_visits")
    public String myVisits(@RequestParam(value = "date", required = false, defaultValue = "2021-01-01") String date,
                           Model model, Authentication authentication) {

        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        Long userId = user.getIdUser();

        LocalDate localDate = LocalDate.parse(date);

        List<Visit> userVisitList = visitService.findByIdUser(userId);
        List<Visit> selectedVisits = visitService.findByVisitDate(localDate);

        visitService.checkVisitsCanBeDeleteByUser(userVisitList);

        model.addAttribute("selectedVisits", selectedVisits);
        model.addAttribute("userVisitList", userVisitList);
        return "user/my-visits";
    }

    @GetMapping(value = "/visit_delete")
    public String visitDelete(@RequestParam("id") Long id,
                              @RequestParam(value = "date", required = false) String date,
                              Model model, RedirectView redirectView) {

        visitService.deleteById(id);
        if(date == null)
            return "redirect:/user/my_visits";
        else
            return "redirect:/user/my_visits?date=" + date;
    }

}