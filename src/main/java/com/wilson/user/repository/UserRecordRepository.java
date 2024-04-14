package com.wilson.user.repository;

import com.wilson.user.model.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User record repository.
 */
public interface UserRecordRepository extends JpaRepository<UserRecord, Long> {
}