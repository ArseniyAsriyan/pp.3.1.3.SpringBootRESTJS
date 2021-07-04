package com.PP311.mySpringBootApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.PP311.mySpringBootApp.model.Role;
import com.PP311.mySpringBootApp.model.User;
import com.PP311.mySpringBootApp.service.RoleService;
import com.PP311.mySpringBootApp.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@RestController
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping
    public String findAll(Model model, ModelMap modelMap, Principal principal) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        modelMap.addAttribute("current_user", userService.findByLogin(principal.getName()));
        Set<Role> setOfAllRoles = roleService.findAll();
        model.addAttribute("setOfAllRoles", setOfAllRoles);
        model.addAttribute("roles", new ArrayList<>());

        return "/admin/user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(ModelMap modelMap, Principal principal, Model model) {
        User user = new User();
        user.setRoles(roleService.findAll());
        model.addAttribute("user", user);
        modelMap.addAttribute("current_user", userService.findByLogin(principal.getName()));
        return "/admin/user-create";
    }

    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") User user) {
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.getByName(role.getRole()));
        }
        user.setRoles(roles);

        userService.update(user);

        return "redirect:/admin/";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/";
    }



    @PostMapping("/user-update/{id}")
    public String updateUser(User user, @RequestParam(value = "setOfAllRoles", required = false) HashSet<Role> setOfAllRoles) {
        Set<Role> roles = new HashSet<>();
        // пояснения за говнокод: строки ниже позволят корректно записывать роли и не стирать их, если в представлении кто-то забудет их указать
        if(setOfAllRoles!=null) {
            for (Role role : setOfAllRoles) {
                roles.add(roleService.getByName(role.getRole()));
                user.setRoles(roles);
            }
        } else {
            user.setRoles(userService.findById(user.getId()).getRoles());
        }
        user.setPassword(userService.findById(user.getId()).getPassword());
        userService.update(user.getId(), user);

        return "redirect:/admin/";
    }

}
