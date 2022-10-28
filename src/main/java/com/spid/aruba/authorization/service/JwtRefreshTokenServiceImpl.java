package com.spid.aruba.authorization.service;


import com.spid.aruba.authorization.model.AppUser;
import com.spid.aruba.authorization.model.JwtRefreshToken;
import com.spid.aruba.authorization.repository.AppUserRepository;
import com.spid.aruba.authorization.repository.JwtRefreshTokenRepository;
import com.spid.aruba.authorization.security.UserPrincipal;
import com.spid.aruba.authorization.security.jwt.JwtProvider;
import com.spid.aruba.authorization.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;


@Service
public class JwtRefreshTokenServiceImpl implements JwtRefreshTokenService
{
    private static Logger LOGGER = LoggerFactory.getLogger(JwtRefreshTokenServiceImpl.class);
    @Value("${app.jwt.refresh-expiration-in-ms}")
    private Long REFRESH_EXPIRATION_IN_MS;

    private JwtRefreshTokenRepository jwtRefreshTokenRepository;

    private AppUserRepository userRepository;

    private JwtProvider jwtProvider;

    public JwtRefreshTokenServiceImpl(JwtRefreshTokenRepository jwtRefreshTokenRepository, AppUserRepository userRepository, JwtProvider jwtProvider) {
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public JwtRefreshToken createRefreshToken(Long userId)
    {
        LOGGER.debug("[JwtRefreshTokenServiceImpl]-[createRefreshToken] :");
        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken();

        jwtRefreshToken.setTokenId(UUID.randomUUID().toString());
        jwtRefreshToken.setUserId(userId);
        jwtRefreshToken.setCreateDate(LocalDateTime.now());
        jwtRefreshToken.setExpirationDate(LocalDateTime.now().plus(REFRESH_EXPIRATION_IN_MS, ChronoUnit.MILLIS));

        LOGGER.debug("[JwtRefreshTokenServiceImpl]-[createRefreshToken] :", jwtRefreshToken);
        return jwtRefreshTokenRepository.save(jwtRefreshToken);
    }

    @Override
    public AppUser generateAccessTokenFromRefreshToken(String refreshTokenId)
    {
        LOGGER.debug("[JwtRefreshTokenServiceImpl]-[generateAccessTokenFromRefreshToken] :");
        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findById(refreshTokenId).orElseThrow();

        if (jwtRefreshToken.getExpirationDate().isBefore(LocalDateTime.now()))
        {
            throw new RuntimeException("JWT refresh token is not valid.");
        }

        AppUser user = userRepository.findById(jwtRefreshToken.getUserId()).orElseThrow();

        UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Set.of(SecurityUtils.convertToAuthority(user.getRole().name())))
                .build();

        String accessToken = jwtProvider.generateToken(userPrincipal);


        LOGGER.debug("[JwtRefreshTokenServiceImpl]-[generateAccessTokenFromRefreshToken] :", accessToken, refreshTokenId);
        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshTokenId);

        return user;
    }
}
