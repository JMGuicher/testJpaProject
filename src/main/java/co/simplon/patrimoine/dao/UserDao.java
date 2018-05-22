package co.simplon.patrimoine.dao;

import co.simplon.patrimoine.model.City;
import co.simplon.patrimoine.model.User;

public interface UserDao {

	User createUser(User user);
    User getUserById(Long id);
    User updateUser(User user);
    void deleteUserById(Long id);

	
}
