package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;


@RestController
public class MainRestController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public MainRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/getAuthorizedUser")
    public ResponseEntity<?> getAuthorizedUser() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        final List<User> userDtoList = userService.index();

        return userDtoList != null && !userDtoList.isEmpty()
                ? new ResponseEntity<>(userDtoList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getAllRoles")
    public ResponseEntity<Iterable<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserFromID(@PathVariable long id) {
        final User userDto = userService.show(id);

        return userDto != null
                ? new ResponseEntity<>(userDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("new" + user);
        userService.save(user);
        System.out.println("new 2" + user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        System.out.println("update" + user);
        userService.update(user.getId(), user);
        System.out.println("update 2" + user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
