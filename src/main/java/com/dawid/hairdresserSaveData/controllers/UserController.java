package com.dawid.hairdresserSaveData.controllers;

import com.dawid.hairdresserSaveData.entity.*;
import com.dawid.hairdresserSaveData.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    UserDetailsService userDetailsService;
    UserDataService userDataService;
    SignUpService signUpService;
    RoleService roleService;

    @Autowired
    public UserController(UserDetailsService userDetailsService, UserDataService userDataService,
                          SignUpService signUpService, RoleService roleService){
        this.userDetailsService = userDetailsService;
        this.userDataService = userDataService;
        this.signUpService = signUpService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/user_panel")
    public String userPanel(Model model, Authentication authentication){

        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "user/user-panel";
    }

    @GetMapping(value="/edit_user")
    public String userPanelEdit(Model model, Authentication authentication){

        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        UserData userData = user.getUserData();
        model.addAttribute("userData", userData);

        return "user/user-panel-form";
    }

    @PostMapping(value= "/edited_user")
    public String editedUser(@ModelAttribute("newUser") UserData userData){

        userDataService.save(userData);
        return "redirect:/user/user_panel";
    }

    @GetMapping("/give_grants")
    public String giveGrants(@RequestParam(value = "name", required = false) String name, Model model){

        Optional<Role> roleAdmin = roleService.findById(2L);
        Optional<Role> roleBoss = roleService.findById(3L);
        Role roleAdmin2 = roleAdmin.get();
        Role roleBoss2 = roleBoss.get();
        List<User> usersAdmin = roleAdmin2.getUsers();

        usersAdmin = usersAdmin.stream().filter(user-> {
            if(user.getRoles().contains(roleBoss2))
                return false;
            else return true;
        }).collect(Collectors.toList());


        List<UserData> userDataList = userDataService.findByFirstName(name);

        List<User> users = new ArrayList<>(Collections.emptyList());
        userDataList.stream().filter(userData -> {

            User user = signUpService.findByUserData(userData);
            if(user.getRoles().contains(roleAdmin2) || user.getRoles().contains(roleBoss2))
                return false;
            else {
                users.add(user);
                return true;
            }

        }).collect(Collectors.toList());

        model.addAttribute("usersToGetGrants", users);
        model.addAttribute("usersAdmin", usersAdmin);
        return "user/give-grants";
    }

    @GetMapping("/delete_grants")
    public String deleteGrants(@PathParam("idUser") Long idUser){

        User user = signUpService.findById(idUser);

        List<Role> userRoles = user.getRoles();
        Optional<Role> role = roleService.findById(2L); //ADMIN role

        userRoles.remove(role.get());
        user.setRoles(userRoles);

        signUpService.save(user);
        return "redirect:/user/give_grants";
    }

    @GetMapping("/give_grants_save")
    public String giveGrantsSave(@PathParam("idUser") Long idUser){

        User user = signUpService.findById(idUser);

        List<Role> userRoles = user.getRoles();
        Optional<Role> role = roleService.findById(2L); //ADMIN role
        userRoles.add(role.get());
        user.setRoles(userRoles);

        signUpService.save(user);
        return "redirect:/user/give_grants";
    }
}
