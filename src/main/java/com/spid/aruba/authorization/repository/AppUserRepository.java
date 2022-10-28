package com.spid.aruba.authorization.repository;

import com.spid.aruba.authorization.model.AppRole;
import com.spid.aruba.authorization.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findAppUserByCodiceFiscale(String codiceFiscale);
    Optional<AppUser> findAppUserByEmail(String email);

    @Modifying
    @Query("update AppUser set role = :role where email = :email")
    void updateUserRole(@Param("email") String email, @Param("role") AppRole role);
}
