package com.dps.ring2park.dao.impl;

import org.springframework.stereotype.Repository;

import com.dps.ring2park.dao.RoleDao;
import com.dps.ring2park.domain.Role;

/**
 * Role DAO implementation.
 * @author Kevin A. Lee
 * 
 */
@Repository("RoleDao")
public class RoleDaoImpl extends GenericDAOImpl<Role, String> implements RoleDao {

}
