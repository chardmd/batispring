package library.modules.app.services;


import library.modules.app.dto.MealDTO;
import library.modules.app.model.Meal;
import library.modules.app.model.SearchResult;
import library.modules.app.model.User;
import library.modules.app.persistence.MealMapper;
import library.modules.app.persistence.UserMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static library.modules.app.utils.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.notNull;

/**
 *
 * Business service for Meal-related operations.
 *
 */
@Service
public class MealService {

    private static final Logger LOGGER = Logger.getLogger(MealService.class);

    @Autowired
    MealMapper mealMapper;

    @Autowired
    UserMapper userMapper;

    /**
     *
     * searches meals by date/time
     *
     * @param username - the currently logged in user
     * @param fromDate - search from this date, including
     * @param toDate - search until this date, including
     * @param fromTime - search from this time, including
     * @param toTime - search to this time, including
     * @param pageNumber - the page number (each page has 10 entries)
     * @return - the found results
     */
    @Transactional(readOnly = true)
    public SearchResult<Meal> findMeals(String username, Date fromDate, Date toDate, Time fromTime, Time toTime, int pageNumber) {

        if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException("Both the from and to date are needed.");
        }

        if (fromDate.after(toDate)) {
            throw new IllegalArgumentException("From date cannot be after to date.");
        }

        if (fromDate.equals(toDate) && fromTime != null && toTime != null && fromTime.after(toTime)) {
            throw new IllegalArgumentException("On searches on the same day, from time cannot be after to time.");
        }

        pageNumber = (pageNumber - 1) * 10;

        Long resultsCount = mealMapper.countMealsByDateTime(username, fromDate, toDate, fromTime, toTime);

        List<Meal> meals = mealMapper.findMealsByDateTime(username, fromDate, toDate, fromTime, toTime, pageNumber);

        return new SearchResult<>(resultsCount, meals);
    }

    /**
     *
     * deletes a list of meals, given their Ids
     *
     * @param deletedMealIds - the list of meals to delete
     */
    @Transactional
    public void deleteMeals(List<Long> deletedMealIds) {
        notNull(deletedMealIds, "deletedMealsId is mandatory");
        deletedMealIds.stream().forEach((deletedMealId) -> mealMapper.delete(deletedMealId));
    }

    /**
     *
     * saves a meal (new or not) into the database.
     *
     * @param username - - the currently logged in user
     * @param id - the database ud of the meal
     * @param date - the date the meal took place
     * @param time - the time the meal took place
     * @param description - the description of the meal
     * @param calories - the calories of the meal
     * @return - the new version of the meal
     */

    @Transactional
    public Meal saveMeal(String username, Long id, Date date, Time time, String description, Long calories) {

        assertNotBlank(username, "username cannot be blank");
        notNull(date, "date is mandatory");
        notNull(time, "time is mandatory");
        notNull(description, "description is mandatory");
        notNull(calories, "calories is mandatory");

        Meal meal = null;

        if (id != null) {

            meal = mealMapper.findMealById(id);

            meal.setDate(date);
            meal.setTime(time);
            meal.setDescription(description);
            meal.setCalories(calories);

            mealMapper.update(id, meal);

        } else {
            User user = userMapper.findUserByUsername(username);

            if (user != null) {

                mealMapper.save(new Meal(user.getId(), date, time, description, calories), user);

                LOGGER.warn("A meal was attempted to be saved for a non-existing user: " + username);
            }

            meal = mealMapper.getLastInsertedMeal();
        }

        return meal;
    }

    /**
     *
     * saves a list of meals (new or not) into the database
     *
     * @param username - the currently logged in user
     * @param meals - the list of meals to be saved
     * @return - the new versions of the saved meals
     */
    @Transactional
    public List<Meal> saveMeals(String username, List<MealDTO> meals) {
        return meals.stream()
                .map((meal) -> saveMeal(
                        username,
                        meal.getId(),
                        meal.getDate(),
                        meal.getTime(),
                        meal.getDescription(),
                        meal.getCalories()))
                .collect(Collectors.toList());
    }
}
