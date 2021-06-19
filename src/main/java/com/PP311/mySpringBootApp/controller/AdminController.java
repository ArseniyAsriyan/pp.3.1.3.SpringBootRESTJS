package com.PP311.mySpringBootApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.PP311.mySpringBootApp.model.Role;
import com.PP311.mySpringBootApp.model.User;
import com.PP311.mySpringBootApp.service.RoleService;
import com.PP311.mySpringBootApp.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @GetMapping
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "/admin/user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user) {
        return "/admin/user-create";
    }

    @PostMapping("/user-create")
    public String createUser(User user) {
        Set<Role> setRoles = new HashSet<>();
        setRoles.add(roleService.getByName("ROLE_USER"));
        User temp = new User(
                user.getLogin(), user.getPassword(),
                user.getName(), user.getSurname(),
                user.getAge(), user.getEmail(),
                setRoles);

        userService.saveUser(temp);
        return "redirect:/admin/";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        Set<Role> setOfAllRoles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("setOfAllRoles", setOfAllRoles);
        model.addAttribute("roles", new ArrayList<>());
        return "/admin/user-update";
    }


    @PostMapping("/user-update/")
    public String updateUser(User user, long id,@ModelAttribute("userRoles") ArrayList<Role> userRoles) {
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.getByName(role.getRole()));
        }
        user.setRoles(roles);
        userService.update(user.getId(), user);

        return "redirect:/admin/";
    }

}
