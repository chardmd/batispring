package library.modules.app.services;

import library.modules.app.model.User;
import library.modules.app.persistence.UserMapper;
import library.modules.app.utils.ValidationUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

/**
 *
 * Business service for User entity related operations
 *
 */
@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private static final Long DEFAULT_MAX_CAL_PER_DAY = 2000L;

    private static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}");

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Autowired
    private UserMapper userMapper;

    /**
     *
     * updates the maximum calories of a given user
     *
     * @param username - the currently logged in user
     * @param newMaxCalories - the new max daily calories for the user
     */
    @Transactional
    public void updateUserMaxCaloriesPerDay(String username, Long newMaxCalories) {
        User user = userMapper.findUserByUsername(username);

        if (user != null) {
            user.setMaxCaloriesPerDay(newMaxCalories);
        } else {
            LOGGER.info("User with username " + username + " could not have the max calories updated.");
        }
    }

    /**
     *
     * creates a new user in the database
     *
     * @param username - the username of the new user
     * @param email - the user email
     * @param password - the user plain text password
     */
    @Transactional
    public void createUser(String username, String email, String password) {

        ValidationUtils.assertNotBlank(username, "Username cannot be empty.");
        ValidationUtils.assertMinimumLength(username, 6, "Username must have at least 6 characters.");
        ValidationUtils.assertNotBlank(email, "Email cannot be empty.");
        ValidationUtils.assertMatches(email, EMAIL_REGEX, "Invalid email.");
        ValidationUtils.assertNotBlank(password, "Password cannot be empty.");
        ValidationUtils.assertMatches(password, PASSWORD_REGEX, "Password must have at least 6 characters, with 1 numeric and 1 uppercase character.");

        User user = userMapper.isUsernameAvailable(username);

        if (user != null) {
            throw new IllegalArgumentException("The username is not available.");
        }

        User newUser = new User(username, new BCryptPasswordEncoder().encode(password), email, DEFAULT_MAX_CAL_PER_DAY);

        userMapper.save(newUser);
    }

    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {

        User user = userMapper.findUserByUsername(username);

        return user;
    }

    @Transactional(readOnly = true)
    public Long findTodaysCaloriesForUser(String username) {

        Long calories = userMapper.findTodaysCaloriesForUser(username);

        return calories;
    }

}
