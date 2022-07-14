package com.noose.storemanager.web.controller;

import com.noose.storemanager.web.dto.RequestAdminForm;
import com.noose.storemanager.domain.ex.IllegalPhoneNumber;
import com.noose.storemanager.service.AdminService;
import com.noose.storemanager.web.dto.ResponseAdmin;
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
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/admins/new")
    public String createForm(Model model) {
        model.addAttribute("requestAdminForm", new RequestAdminForm());
        return "members/createAdminForm";
    }

    @PostMapping("/admins/new")
    public String create(@Valid RequestAdminForm adminForm, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createAdminForm";
        }

        try {
            adminService.joinAdmin(adminForm.toEntity());
        } catch (IllegalPhoneNumber e) {
            result.rejectValue("phoneNumber", "", e.getMessage());
            return "members/createAdminForm";
        }

        return "redirect:/";
    }

    @GetMapping("/admins")
    public String getAdmins(Model model) {
        List<ResponseAdmin> admins = adminService.findAdmins()
                .stream()
                .map(ResponseAdmin::createFrom)
                .collect(Collectors.toList());

        model.addAttribute("admins", admins);

        return "members/adminList";
    }

}
