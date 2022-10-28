package com.spid.aruba.authorization.controller;

import com.spid.aruba.authorization.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/admin")//pre-path
public class AdminController
{
    private static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    private AppUserService userService;

    public AdminController(AppUserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")//api/admin/all
    public ResponseEntity<?> findAllUsers()
    {
        LOGGER.debug("[AdminController] - [findAllUsers]");
        return ResponseEntity.ok(userService.findAllUsers());
    }
}
