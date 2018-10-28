package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.dao.api.RoleDao;
import system.entity.Role;

/**
 *Implementation of {@link RoleDao} interface.
 */
@Repository
public class RoleDaoImpl extends JpaDao<Long, Role> implements RoleDao {
}
