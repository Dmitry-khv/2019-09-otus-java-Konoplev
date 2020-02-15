package ru.otus.cache.hibernate.repository;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.api.repository.UserRepository;
import ru.otus.cache.api.repository.UserRepositoryException;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.sessionmanager.SessionManager;
import ru.otus.cache.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.cache.hibernate.sessionmanager.SessionManagerHibernate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class UserRepositoryHibernate implements UserRepository {
    private static Logger logger = LoggerFactory.getLogger(UserRepositoryHibernate.class);

    private final SessionManagerHibernate sessionManagerHibernate;

    public UserRepositoryHibernate(SessionManagerHibernate sessionManagerHibernate) {
        this.sessionManagerHibernate = sessionManagerHibernate;
    }


    @Override
    public long save(User user) {
        DatabaseSessionHibernate currentSession = sessionManagerHibernate.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (user.getId() > 0)
                hibernateSession.merge(user);
            else
                hibernateSession.persist(user);
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public User findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManagerHibernate.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            User user = hibernateSession.find(User.class, id);
            return user;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManagerHibernate;
    }

    @Override
    public List<User> getListUsers() {
        sessionManagerHibernate.beginSession();
        DatabaseSessionHibernate currentSession = sessionManagerHibernate.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            CriteriaBuilder cb = hibernateSession.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rootEntry = cq.from(User.class);
            CriteriaQuery<User> all = cq.select(rootEntry);
            TypedQuery<User> allQuery = hibernateSession.createQuery(all);
            return allQuery.getResultList();
//            return hibernateSession.createQuery("SELECT * form User", User.class).getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(e);
        }
    }

}
