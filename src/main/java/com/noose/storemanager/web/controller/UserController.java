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
import org.springframework.web.bind.annotation.*;

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
    public String getUsers(@RequestParam(required = false) String query, Model model) {
        List<ResponseUser> users = userService.findUsers(query)
                .stream()
                .map(ResponseUser::createFrom)
                .collect(Collectors.toList());

        model.addAttribute("users", users);
        return "members/memberList";
    }

    @GetMapping("/members/{userId}/edit")
    public String updateItemForm(@PathVariable("userId") Long userId, Model model) {
        ResponseUser user = ResponseUser.createFrom(userService.findOne(userId));
        model.addAttribute("user", user);
        return "members/updateMemberForm";
    }
    @PostMapping("/members/{userId}/edit")
    public String updateItem(@PathVariable("userId") Long userId, @ModelAttribute("form") RequestUserForm form) {
        userService.updateUser(userId, form.getName(), form.getPhoneNumber());

        return "redirect:/members";
    }
}
