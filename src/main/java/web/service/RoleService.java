package web.service;

import web.models.Role;

import java.util.List;

public interface RoleService {

    public void save(Role role);

    public Role getRoleById(Long id);

    public List<Role> getAllRoles();
}
