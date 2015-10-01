package library.modules.app.persistence;

import library.modules.app.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    public User findUserByUsername(@Param("username") String username);

    public Long findTodaysCaloriesForUser(@Param("username") String username);

    public void save(@Param("user") User user);

    public void update(@Param("user") User user);

    public User isUsernameAvailable(@Param("username") String username);

}
