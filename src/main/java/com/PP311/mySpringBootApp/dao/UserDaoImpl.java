package com.PP311.mySpringBootApp.dao;

import org.springframework.stereotype.Repository;
import com.PP311.mySpringBootApp.model.User;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void update(long id, User user) {
        User userForUpdate = findById(id);
        userForUpdate = user;
        userForUpdate.setId(id);
        em.merge(userForUpdate);

    }
    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public List<User> findAll() {
        String hql = "from User";
        Query query = em.createQuery(hql, User.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        em.remove(findById(id));
    }

    @Override
    public User findById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findByLogin(String login) {
        Query query = em.createQuery("from User where login=:login")
                .setParameter("login", login);
        try {
            return (User) query.getSingleResult();
        } catch (Exception e){
            return null;
        }
    }
}
