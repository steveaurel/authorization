package com.spid.aruba.authorization.service;


import com.spid.aruba.authorization.model.AppUser;

public interface AuthenticationService
{
    AppUser signInAndReturnJWT(AppUser signInRequest);
}
