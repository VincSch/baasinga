package com.vschwarzer.baasinga.repository.authorization;

import com.vschwarzer.baasinga.domain.model.Role;
import com.vschwarzer.baasinga.domain.model.User;
import com.vschwarzer.baasinga.repository.GenericDAO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Defining repository access methods for users
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface UserDAO extends UserDetailsService, GenericDAO<User> {

    /**
     * Find user by specified email.
     *
     * @param email the email
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Returns all user by role.
     *
     * @param role role entity
     * @return all users by role
     */
    List<User> findByRole(final Role role);

    /**
     * Returns all enabled user between start and count. SET WHERE user.enable =
     * true. IF start and count contains 0, than return first user. IF start
     * contains 0 and count contains 1, than return first user.
     *
     * @param start index for first.
     * @param count index for last.
     * @return users
     */
    List<User> find(Integer start, Integer count);

    /**
     * Returns all user by role id.
     *
     * @param roleId role id
     * @return all users by role id
     */
    List<User> findByRoleId(final Long roleId);

    /**
     * Returns all non admin users.
     *
     * @return all non admin users
     */
    List<User> findAllNonAdminUsers();

    /**
     * Returns non admin users, which are en- or diabled
     * users else otherwise.
     *
     * @param enabled true, false
     * @return non admin users
     */
    List<User> findNonAdminUsersByEnabled(boolean enabled);

    /**
     * Returns all users by role which enabled .
     *
     *
     * @param roleName
     * @param enabled true, false
     * @return not admin users
     */
    List<User> findByRoleAndEnabled(final String roleName, boolean enabled);

    /**
     * Returns not admin users. If locked is true, than all not admin users
     * which account is not locked else otherwise
     *
     * @param locked true, false
     * @return not admin users
     */
    List<User> findByAccountLocked(boolean locked);

    /**
     * Returns user by its id.
     * Note this method fetch all user data.
     * To find only user, use findOne.
     *
     * @param id user id.
     * @return all user data.
     */
    User findById(Long id);

    /**
     * Find user by specified passwordResetToken.
     *
     * @param passwordResetToken the passwordResetToken
     * @return the user
     */
    User findByPasswordResetToken(final String passwordResetToken);

}
