package com.wilson.user.repository;

import com.wilson.user.model.UserRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type User record repository test.
 */
@DataJpaTest
public class UserRecordRepositoryTest {

    @Autowired
    private UserRecordRepository userRecordRepository;

    private UserRecord testUserRecord;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        testUserRecord = new UserRecord(null, "John Doe", "john.doe@example.com");
    }

    /**
     * Cleanup.
     */
    @AfterEach
    public void cleanup() {
        userRecordRepository.deleteAll();
    }

    /**
     * When save user then succeed.
     */
    @Test
    public void whenSaveUserThenSucceed() {
        UserRecord savedUserRecord = userRecordRepository.save(testUserRecord);
        assertThat(savedUserRecord).isNotNull();
        assertThat(savedUserRecord.getId()).isNotNull();
        assertThat(savedUserRecord.getName()).isEqualTo("John Doe");
        assertThat(savedUserRecord.getEmail()).isEqualTo("john.doe@example.com");
    }

    /**
     * When find by id then succeed.
     */
    @Test
    public void whenFindByIdThenSucceed() {
        UserRecord savedUserRecord = userRecordRepository.save(testUserRecord);
        Optional<UserRecord> foundUser = userRecordRepository.findById(savedUserRecord.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(savedUserRecord);
    }

    /**
     * When find all then succeed.
     */
    @Test
    public void whenFindAllThenSucceed() {
        UserRecord userRecord1 = userRecordRepository.save(new UserRecord(null, "John Doe", "john.doe@example.com"));
        UserRecord userRecord2 = userRecordRepository.save(new UserRecord(null, "Jane Smith", "jane.smith@example.com"));
        List<UserRecord> userRecords = userRecordRepository.findAll();
        assertThat(userRecords).hasSize(2);
        assertThat(userRecords).containsExactlyInAnyOrder(userRecord1, userRecord2);
    }
}