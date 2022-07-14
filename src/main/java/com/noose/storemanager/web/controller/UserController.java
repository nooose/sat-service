package com.noose.storemanager.web.controller;


import com.noose.storemanager.domain.ex.IllegalPhoneNumber;
import com.noose.storemanager.service.UserService;
import com.noose.storemanager.web.dto.RequestAdminForm;
import com.noose.storemanager.web.dto.RequestUserForm;
import com.noose.storemanager.web.dto.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("requestUserForm", new RequestAdminForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid RequestUserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        try {
            userService.joinUser(userForm.toEntity());
        } catch (IllegalPhoneNumber e) {
            result.rejectValue("phoneNumber", "", e.getMessage());
            return "members/createMemberForm";
        }

        return "redirect:/";
    }

    @GetMapping("/members")
    public String getUsers(Model model) {
        List<ResponseUser> users = userService.findUsers()
                .stream()
                .map(ResponseUser::createFrom)
                .collect(Collectors.toList());

        model.addAttribute("users", users);
        return "members/memberList";
    }

}
