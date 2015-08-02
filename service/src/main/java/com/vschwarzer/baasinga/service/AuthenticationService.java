package com.vschwarzer.baasinga.service;

import com.vschwarzer.baasinga.domain.dto.common.ChangePasswordDTO;
import com.vschwarzer.baasinga.domain.dto.common.ChangeProfileDTO;
import com.vschwarzer.baasinga.domain.dto.common.RegisterDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;

/**
 * Created by Vincent Schwarzer on 27.07.15.
 */
public interface AuthenticationService {

    boolean isPasswordMatching(String password, String confirmPassword);

    boolean isEmailTaken(String email);

    void createUser(RegisterDTO registerDTO);

    void changePassword(User user, ChangePasswordDTO changePasswordDTO);

    void changeUserDetails(User user, ChangeProfileDTO changeProfileDTO);
}
