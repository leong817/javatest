package com.wilson.user.service;

import com.wilson.user.model.UserRecord;

import java.util.List;

/**
 * The interface User record service.
 */
public interface UserRecordService {
    /**
     * Create user user record.
     *
     * @param userRecord the user record
     * @return the user record
     */
    UserRecord createUser(UserRecord userRecord);

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    UserRecord getUserById(Long id);

    /**
     * Gets all users.
     *
     * @return the all users
     */
    List<UserRecord> getAllUsers();

    /**
     * Update user user record.
     *
     * @param id         the id
     * @param userRecord the user record
     * @return the user record
     */
    UserRecord updateUser(Long id, UserRecord userRecord);

    /**
     * Delete user.
     *
     * @param id the id
     */
    void deleteUser(Long id);
}