package com.vschwarzer.baasinga.service.impl;

import com.vschwarzer.baasinga.domain.dto.common.ChangePasswordDTO;
import com.vschwarzer.baasinga.domain.dto.common.ChangeProfileDTO;
import com.vschwarzer.baasinga.domain.dto.common.RegisterDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.repository.authorization.UserDAO;
import com.vschwarzer.baasinga.service.AuthenticationService;
import com.vschwarzer.baasinga.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Vincent Schwarzer on 27.07.15.
 */
@Service
public class AuthenticationServiceImpl extends BaseService implements AuthenticationService {

    @Autowired
    UserDAO userDAO;

    @Override
    public boolean isPasswordMatching(String password, String confirmPassword) {
        boolean result = false;
        if (password.equals(confirmPassword)) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean isEmailTaken(String email) {
        boolean result = false;
        if (userDAO.findByEmail(email) != null) {
            result = true;
        }
        return result;
    }

    @Override
    public void createUser(RegisterDTO registerDTO) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUsername(registerDTO.getEmail());
        user.setPassword(encoder.encode(registerDTO.getPassword()));
        user.setLastName((registerDTO.getLastname().isEmpty()) ? " " : registerDTO.getLastname());
        user.setFirstName((registerDTO.getFirstname().isEmpty()) ? " " : registerDTO.getFirstname());
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        userDAO.create(user);

    }

    public void changeUserDetails(User user, ChangeProfileDTO changeProfileDTO) {
        user.setUsername(changeProfileDTO.getEmail());
        if (!changeProfileDTO.getFirstname().isEmpty())
            user.setFirstName(changeProfileDTO.getFirstname());
        if (!changeProfileDTO.getLastname().isEmpty())
            user.setLastName(changeProfileDTO.getLastname());
        userDAO.update(user);
    }

    public void changePassword(User user, ChangePasswordDTO changePasswordDTO) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(changePasswordDTO.getNewPw()));
        userDAO.update(user);
    }
}
