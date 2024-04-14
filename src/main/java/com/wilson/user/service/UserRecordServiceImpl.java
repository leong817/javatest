package com.wilson.user.service;

import com.wilson.user.exception.ResourceNotFoundException;
import com.wilson.user.model.EmailRequest;
import com.wilson.user.model.UserRecord;
import com.wilson.user.repository.UserRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type User record service.
 */
@Service
public class UserRecordServiceImpl implements UserRecordService {

    private final UserRecordRepository userRecordRepository;

    private final ValidationService validationService;

    private final JavaMailSenderImpl javaMailSender;

    /**
     * Instantiates a new User record service.
     *
     * @param userRecordRepository the user record repository
     * @param validationService    the validation service
     * @param javaMailSender       the java mail sender
     */
    @Autowired
    public UserRecordServiceImpl(@NotNull UserRecordRepository userRecordRepository,
                                 @NotNull ValidationService validationService,
                                 @NotNull JavaMailSenderImpl javaMailSender) {
        this.userRecordRepository = userRecordRepository;
        this.validationService = validationService;
        this.javaMailSender = javaMailSender;
        String hostname = javaMailSender.getHost();
    }

    /**
     * Creates a user record.
     *
     * @param userRecord the user record
     * @return the UserRecord or null.
     */
    @Override
    public UserRecord createUser(@NotNull @Valid UserRecord userRecord) {
        if (validationService.validateUser(userRecord)) {
            UserRecord userRecordSaved = userRecordRepository.save(userRecord);
            composeEmail(userRecordSaved);
            return userRecordSaved;
        }
        return null;
    }

    /**
     * Compose an email based from a user record.
     *
     * @param userRecordSaved the user record which contains details for the email.
     */
    private void composeEmail(@NotNull UserRecord userRecordSaved) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setRecipientEmail(userRecordSaved.getEmail());
        emailRequest.setSubject("User Successfully Registered!!");
        emailRequest.setBody("Congratulations for registering with us!\n" +
                "\n" +
                "Welcome!\n\n" +
                "If you are viewing this email, you are now subscribed to our service.\n\n" +
                "Enjoy your stay!\n\n" +
                "Cheers!\n\n"+
                "Admin\n");
        sendEmail(emailRequest);
    }

    /**
     * Get User by user id.
     *
     * @param id the user id which contains the data to search.
     * @return UserRecord the record found for the matched id.
     */
    @Override
    public UserRecord getUserById(@Valid @NotNull Long id) {
        return userRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    /**
     * Get all Users.
     *
     * @return List<UserRecord> List of all UserRecord the record found for the matched id.
     */
    @Override
    public List<UserRecord> getAllUsers() {
        return userRecordRepository.findAll().stream()
                .filter(validationService::validateUser)
                .collect(Collectors.toList());
    }

    /**
     * Updates the user query through by user id and replace it with the passed user record.
     *
     * @param id  represents the user id
     * @param userRecord the record which will replace the old record.
     * @return UserRecord UserRecord the record found for the matched id.
     */
    @Override
    public UserRecord updateUser(Long id, UserRecord userRecord) {
        UserRecord existingUserRecord = userRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (validationService.validateUser(userRecord)) {
            existingUserRecord.setName(userRecord.getName());
            existingUserRecord.setEmail(userRecord.getEmail());
            return userRecordRepository.save(existingUserRecord);
        }
        return null;
    }

    /**
     * Delete the user by query through id.
     *
     * @param id  represents the user id to delete
     * @throws ResourceNotFoundException if the user is not found.
     */
    @Override
    public void deleteUser(Long id) {
        UserRecord existingUserRecord = userRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRecordRepository.delete(existingUserRecord);
    }

    /**
     * Send email string.
     *
     * @param emailRequest the email request
     * @return the string
     */
    public String sendEmail(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailRequest.getFromEmail());
        message.setTo(emailRequest.getRecipientEmail());
        message.setSubject(emailRequest.getSubject());
        message.setText(emailRequest.getBody());
        javaMailSender.send(message);
        return "Email sent successfully!";
    }
}
