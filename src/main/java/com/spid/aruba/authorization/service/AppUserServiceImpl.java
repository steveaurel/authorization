package com.spid.aruba.authorization.service;

import com.spid.aruba.authorization.model.AppRole;
import com.spid.aruba.authorization.model.AppUser;
import com.spid.aruba.authorization.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{

    private static Logger LOGGER = LoggerFactory.getLogger(AppUserServiceImpl.class);
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(null == user.getRole() || "".equals(user.getRole())){
            user.setRole(AppRole.USER);
        }
        user.setCreateTime(LocalDateTime.now());
        LOGGER.debug("[AppUserServiceImpl]-[saveUser] :",user);
        return userRepository.save(user);
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        LOGGER.debug("[AppUserServiceImpl]-[findByEmail] :",email);
        return userRepository.findAppUserByEmail(email);
    }

    @Override
    public Optional<AppUser> findByCodiceFiscale(String codiceFiscale) {
        LOGGER.debug("[AppUserServiceImpl]-[findByCodiceFiscale] :",codiceFiscale);
        return userRepository.findAppUserByCodiceFiscale(codiceFiscale);
    }

    @Override
    public void changeRole(AppRole newRole, String email) {
        LOGGER.debug("[AppUserServiceImpl]-[findByCodiceFiscale] :",newRole,email);
        userRepository.updateUserRole(email, newRole);
    }

    @Override
    public List<AppUser> findAllUsers() {

        LOGGER.debug("[AppUserServiceImpl]-[findAllUsers] ");
        return userRepository.findAll();
    }
}
