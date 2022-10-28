package com.spid.aruba.authorization.repository;

import com.spid.aruba.authorization.model.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String>
{
}
