package com.dawid.hairdresserSaveData.controllers;

import com.dawid.hairdresserSaveData.component.mailBuilder.SignUpMail;
import com.dawid.hairdresserSaveData.entity.PriceList;
import com.dawid.hairdresserSaveData.entity.User;
import com.dawid.hairdresserSaveData.entity.UserData;
import com.dawid.hairdresserSaveData.repository.UserRepository;
import com.dawid.hairdresserSaveData.services.PriceListService;
import com.dawid.hairdresserSaveData.services.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class WebController {

    PriceListService priceListService;
    SignUpService signUpService;
    UserRepository userRepository;
    SignUpMail signUpMail;

    @GetMapping("/contact")
    public String contact() throws MessagingException {
        //signUpMail.sendConfirmationLinkByWp("dawid.990m@gmail.com", "aaa");
        return "webStatic/contact";
    }

    @PostMapping(value="/sign_up")
    public String signUp(@RequestParam("username") String username, @RequestParam("password") String password, //username - email
                         @RequestParam("phoneNumber") String phoneNumber, @RequestParam("firstName") String firstName,
                         @RequestParam("secondName") String secondName){

        Optional<User> optionalUser = userRepository.findByEmail(username);

        if(optionalUser.isEmpty()) {
            User user = User.of(username, password);
            UserData userData = new UserData(firstName, secondName, phoneNumber, LocalDateTime.now());
            signUpService.signUpUser(user, userData);
            return "redirect:/login?signUpSuccess";
        }else
            return "redirect:/sign_up?emailError";
    }


    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/sign_up")
    public String signUp() {
        return "webStatic/sign-up";
    }

    @GetMapping(value = "/price_list")
    public String priceList(Model model) {
        List<PriceList> listMan = priceListService.findByCategory("man");
        List<PriceList> listWoman = priceListService.findByCategory("woman");
        List<PriceList> listColorization = priceListService.findByCategory("colorization");
        List<PriceList> listServices = priceListService.findByCategory("otherServices");

        model.addAttribute("listMan", listMan);
        model.addAttribute("listWoman", listWoman);
        model.addAttribute("listColorization", listColorization);
        model.addAttribute("otherServices", listServices);
        return "webStatic/price-list";
    }

    @GetMapping(value = "/confirm_email")
    public String confirmEmail(@RequestParam(name = "token") String token){

        Optional<User> optionalUser = userRepository.findByConfirmationToken(token);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled(true);
            userRepository.save(user);
            return "redirect:/login?confirmEmailSuccess";
        }else{
            return "redirect:/login?confirmEmailError";
        }
    }
}
