package web.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.models.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, Long> {

}
