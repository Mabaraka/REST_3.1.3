package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> index();

    public User show(Long id);

    public void save(User user);

    public void update(Long id, User user);

    public void delete(Long id);

    public User findByUsername(String username);

}
