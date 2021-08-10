package web.util;

import org.springframework.stereotype.Component;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {

    private UserService userService;
    private RoleService roleService;

    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void Init() {
        Role roleAdmin = new Role(1L,"ROLE_ADMIN");
        Role roleUser = new Role(2L,"ROLE_USER");

        Set<Role> allRoles = new HashSet<>();
        allRoles.add(roleAdmin);
        allRoles.add(roleUser);

        for (Role role: allRoles) {
            roleService.save(role);
        }

        User admin = new User("Bob","Miller","bob@gmail.com","pass",21);
        admin.setRoles(allRoles);
        userService.save(admin);

        User user = new User("Rik","Stone","rik@gmail.com","pass",20);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.getRoleById(2L));
        user.setRoles(userRoles);
        userService.save(user);
    }
}