package library.modules.app.persistence;

import library.modules.app.model.Meal;
import library.modules.app.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface MealMapper {


    public long countMealsByDateTime(@Param("username") String username, @Param("fromDate") Date fromDate,
                                    @Param("toDate") Date toDate, @Param("fromTime") Time fromTime, @Param("toTime") Time toTime);

    public List<Meal> findMealsByDateTime(@Param("username") String username, @Param("fromDate") Date fromDate,
                                          @Param("toDate") Date toDate, @Param("fromTime") Time fromTime, @Param("toTime") Time toTime,
                                          @Param("offset") int offset);

    public Meal findMealById(@Param("mealId") long mealId);

    public Meal getLastInsertedMeal();

    public void delete(@Param("mealId") long mealId);

    public void save(@Param("meal") Meal meal, @Param("user") User user);

    public void update(@Param("mealId") long mealId, @Param("meal") Meal meal);
}
