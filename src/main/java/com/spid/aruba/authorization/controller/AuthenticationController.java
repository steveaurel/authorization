package com.spid.aruba.authorization.controller;


import com.spid.aruba.authorization.model.AppUser;
import com.spid.aruba.authorization.service.AppUserService;
import com.spid.aruba.authorization.service.AuthenticationService;
import com.spid.aruba.authorization.service.JwtRefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authentication")//pre-path
public class AuthenticationController
{
    private AuthenticationService authenticationService;

    private AppUserService userService;

    private JwtRefreshTokenService jwtRefreshTokenService;

    public AuthenticationController(AuthenticationService authenticationService, AppUserService userService, JwtRefreshTokenService jwtRefreshTokenService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
    }

    @PostMapping("sign-up")//api/authentication/sign-up
    public ResponseEntity<?> signUp(@RequestBody AppUser user)
    {
        if (userService.findByEmail(user.getEmail()).isPresent()
                || userService.findByCodiceFiscale(user.getCodiceFiscale()).isPresent())
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("sign-in")//api/authentication/sign-in
    public ResponseEntity<?> signIn(@RequestBody AppUser user)
    {
        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
    }

    @PostMapping("refresh-token")//api/authentication/refresh-token?token=
    public ResponseEntity<?> refreshToken(@RequestParam String token)
    {
        return ResponseEntity.ok(jwtRefreshTokenService.generateAccessTokenFromRefreshToken(token));
    }
}
