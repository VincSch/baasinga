package com.vschwarzer.baasinga.service;

import com.vschwarzer.baasinga.domain.dto.common.ChangePasswordDTO;
import com.vschwarzer.baasinga.domain.dto.common.ChangeProfileDTO;
import com.vschwarzer.baasinga.domain.dto.common.RegisterDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;

/**
 * Created by Vincent Schwarzer on 27.07.15.
 */
public interface AuthenticationService {

    public boolean isPasswordMatching(String password, String confirmPassword);

    public boolean isEmailTaken(String email);

    public void createUser(RegisterDTO registerDTO);

    public void changePassword(User user, ChangePasswordDTO changePasswordDTO);

    public void changeUserDetails(User user, ChangeProfileDTO changeProfileDTO);
}
