package com.PP311.mySpringBootApp.dao;

import org.springframework.stereotype.Repository;
import com.PP311.mySpringBootApp.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Role> findAll() {
        String hql = "from Role";
        Query query = em.createQuery(hql, Role.class);
        List<Role> roles = query.getResultList();
        return new HashSet<>(roles);
    }

    @Override
    public void saveRole(Role role) {
        em.persist(role);
    }

    @Override
    public void update(Role role) {
        em.merge(role);

    }

    @Override
    public void deleteRole(Role role) {
        em.remove(em.find(Role.class, role.getId()));
    }

    @Override
    public Role findByRole(String role) {
        try {
            return (Role) em.createQuery("from Role where role=?0").setParameter(0, role).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
