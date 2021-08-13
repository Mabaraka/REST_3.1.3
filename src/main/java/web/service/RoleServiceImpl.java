package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.models.Role;
import web.models.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    @Transactional
    public Role getRoleById(Long id) {
        return roleDao.findById(id).get();
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        roleDao.findAll().forEach(list::add);
        return list;
    }
}
