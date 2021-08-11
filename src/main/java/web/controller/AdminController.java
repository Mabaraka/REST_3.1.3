package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import javax.naming.Binding;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        model.addAttribute("loggedUser", userService.findByEmail(principal.getName()));
        model.addAttribute("Users", userService.index());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin_show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model, Principal principal) {
        model.addAttribute("loggedUser", userService.findByEmail(principal.getName()));
        return "new";
    }

    @PostMapping("/createUser")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(required = false, name = "ADMIN") String ADMIN,
                         @RequestParam(required = false, name = "USER") String USER,
                         Principal principal, Model model) {

        model.addAttribute("loggedUser", userService.findByEmail(principal.getName()));

        Set<Role> roles = new HashSet<>();

        if (ADMIN != null) {
            roles.add(roleService.getRoleById(1L));
        }
        if (USER != null) {
            roles.add(roleService.getRoleById(2L));
        }
        if (ADMIN == null && USER == null) {
            roles.add(roleService.getRoleById(2L));
        }
        user.setRoles(roles);

        userService.save(user);

        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") Long id,
                         @RequestParam(required = false, name = "ADMIN") String ADMIN,
                         @RequestParam(required = false, name = "USER") String USER) {
        HashSet<Role> roles = new HashSet();

        if (ADMIN != null) {
            roles.add(roleService.getRoleById(1L));
        }
        if (USER != null) {
            roles.add(roleService.getRoleById(2L));
        }
        if (ADMIN == null && USER == null) {
            roles.add(roleService.getRoleById(2L));
        }
        user.setRoles(roles);

        userService.update(id, user);

        return "redirect:/admin";
    }

    @RequestMapping(path = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping("/user")
    public String ShowUser(Model model, Principal principal) {
        model.addAttribute("user", userService.show(userService.findByEmail(principal.getName()).getId()));
        return "show-current-user-for-admin";
    }

}
