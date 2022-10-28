package com.spid.aruba.authorization.service;

import com.spid.aruba.authorization.model.AppUser;
import com.spid.aruba.authorization.security.UserPrincipal;
import com.spid.aruba.authorization.security.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
    private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private AuthenticationManager authenticationManager;

    private JwtProvider jwtProvider;

    private JwtRefreshTokenService jwtRefreshTokenService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtProvider, JwtRefreshTokenService jwtRefreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
    }

    @Override
    public AppUser signInAndReturnJWT(AppUser signInRequest)
    {

        LOGGER.debug("[AuthenticationServiceImpl]-[signInAndReturnJWT] :");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);

        AppUser signInUser = userPrincipal.getUser();
        signInUser.setAccessToken(jwt);
        signInUser.setRefreshToken(jwtRefreshTokenService.createRefreshToken(signInUser.getId()).getTokenId());

        LOGGER.debug("[AuthenticationServiceImpl]-[signInAndReturnJWT] :",signInUser);
        return signInUser;
    }
}
