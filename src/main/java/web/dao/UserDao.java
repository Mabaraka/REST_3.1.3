package web.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.models.User;

import java.util.Optional;


@Repository
public interface UserDao extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
