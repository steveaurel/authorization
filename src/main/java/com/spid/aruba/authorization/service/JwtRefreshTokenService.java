package com.spid.aruba.authorization.service;

import com.spid.aruba.authorization.model.AppUser;
import com.spid.aruba.authorization.model.JwtRefreshToken;


public interface JwtRefreshTokenService
{
    JwtRefreshToken createRefreshToken(Long userId);

    AppUser generateAccessTokenFromRefreshToken(String refreshTokenId);
}
