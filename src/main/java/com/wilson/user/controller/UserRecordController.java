package com.wilson.user.controller;

import com.wilson.user.model.UserRecord;
import com.wilson.user.service.UserRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type User record controller.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Record Controller", description = "User Record Controller")
public class UserRecordController {
    @Autowired
    private UserRecordService userRecordService;

    /**
     * Create user response entity.
     *
     * @param userRecord the user record
     * @return the response entity
     */
    @PostMapping
    @Operation(summary = "This registers a user to the application.", description = "The purpose of this REST API is to persist the user record passed and which can be later retrieved through the REST Get APIs")
    public ResponseEntity<UserRecord> createUser(@Valid @RequestBody UserRecord userRecord) {
        UserRecord createdUserRecord = userRecordService.createUser(userRecord);
        return createdUserRecord != null ? ResponseEntity.status(HttpStatus.CREATED).body(createdUserRecord) : ResponseEntity.badRequest().build();
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    @GetMapping("/{id}")
    @Operation(summary = "This fetches single record of the user.", description = "The purpose of this REST API is to query the user record id passed and if found, it will return the entire user record row found.")
    public ResponseEntity<UserRecord> getUserById(@PathVariable Long id) {
        UserRecord userRecord = userRecordService.getUserById(id);
        return ResponseEntity.ok(userRecord);
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    @GetMapping
    @Operation(summary = "This fetches all records of the user record store.", description = "The purpose of this REST API is to query the entire user records if there is any found, it will return the entire user record rows found.")
    public ResponseEntity<List<UserRecord>> getAllUsers() {
        List<UserRecord> userRecords = userRecordService.getAllUsers();
        return ResponseEntity.ok(userRecords);
    }

    /**
     * Update user response entity.
     *
     * @param id         the id
     * @param userRecord the user record
     * @return the response entity
     */
    @PutMapping("/{id}")
    @Operation(summary = "This replaces the record of the user record store.", description = "The purpose of this REST API is to query the a particular user and if there is any found, it will update that user record found with the one passed.")
    public ResponseEntity<UserRecord> updateUser(@PathVariable Long id, @Valid @RequestBody UserRecord userRecord) {
        UserRecord updatedUserRecord = userRecordService.updateUser(id, userRecord);
        return updatedUserRecord != null ? ResponseEntity.ok(updatedUserRecord) : ResponseEntity.badRequest().build();
    }

    /**
     * Delete user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "This removes the record of the user record store.", description = "The purpose of this REST API is to query the particular user and if there is any found, it will remove the user record found.")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRecordService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}