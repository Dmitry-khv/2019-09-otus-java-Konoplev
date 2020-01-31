package ru.otus.serverHW.database.hibernate.dao;

import org.h2.engine.User;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.serverHW.database.api.dao.UserDao;
import ru.otus.serverHW.database.api.dao.UserDaoException;
import ru.otus.serverHW.database.api.sessionmanager.SessionManager;
import ru.otus.serverHW.database.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.serverHW.database.hibernate.sessionmanager.SessionManagerHibernate;


public class UserDaoHibernate implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManagerHibernate sessionManagerHibernate;

    public UserDaoHibernate(SessionManagerHibernate sessionManagerHibernate) {
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
            throw new UserDaoException(e);
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
            throw new UserDaoException(e);
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

}
