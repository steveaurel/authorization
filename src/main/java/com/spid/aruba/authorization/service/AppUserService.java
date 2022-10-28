package com.spid.aruba.authorization.service;

import com.spid.aruba.authorization.model.AppRole;
import com.spid.aruba.authorization.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService {
    AppUser saveUser(AppUser user);

    Optional<AppUser> findByEmail(String username);
    Optional<AppUser> findByCodiceFiscale(String username);

    void changeRole(AppRole newRole, String email);

    List<AppUser> findAllUsers();
}
