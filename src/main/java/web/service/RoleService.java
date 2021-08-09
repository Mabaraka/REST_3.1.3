package web.service;

import web.models.Role;

public interface RoleService {

    public void save(Role role);

    public Role getRoleById(Long id);
}
