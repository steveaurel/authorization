package com.spid.aruba.authorization.controller;


import com.spid.aruba.authorization.model.AppRole;
import com.spid.aruba.authorization.security.UserPrincipal;
import com.spid.aruba.authorization.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")//pre-path
public class UserController
{
    private AppUserService userService;

    public UserController(AppUserService userService) {
        this.userService = userService;
    }

    @PutMapping("change/{role}")//api/user/change/{role}
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable AppRole role)
    {
        userService.changeRole(role, userPrincipal.getUsername());

        return ResponseEntity.ok(true);
    }
}
