package ru.otus.cachehw;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.cache.HwListener;
import ru.otus.cachehw.cache.MyCache;
import ru.otus.cachehw.hibernate.api.dao.UserDao;
import ru.otus.cachehw.hibernate.api.model.AddressDataSet;
import ru.otus.cachehw.hibernate.api.model.PhoneDataSet;
import ru.otus.cachehw.hibernate.api.model.User;
import ru.otus.cachehw.hibernate.api.service.DBServiceUser;
import ru.otus.cachehw.hibernate.api.service.DBServiceUserImpl;
import ru.otus.cachehw.hibernate.hibernate.HibernateUtils;
import ru.otus.cachehw.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.cachehw.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;

public class CacheDemo {
    private static Logger logger = LoggerFactory.getLogger(CacheDemo.class);

    private static final String HIBERNATE_CONFIG = "hibernate.cfg.xml";
    private static final long USER_ID = 1L;
    private List<Long> id = new ArrayList<>();

    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManager;
    private UserDao userDao;
    private DBServiceUser serviceUser;

    public static void main(String[] args) {
        CacheDemo cacheDemo = new CacheDemo();
        cacheDemo.setUp();
        cacheDemo.run();
    }


    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CONFIG, User.class, AddressDataSet.class, PhoneDataSet.class);
        sessionManager = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManager);
        MyCache<String, User> cache = new MyCache<>();
        HwListener<String, User> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);
        cache.addListener(listener);
        cache.addListener(null);
        serviceUser = new DBServiceUserImpl(userDao, cache);
    }

    private void run() {
        for (int idx = 0; idx < 50; idx++) {
            User user = new User(idx + 1, "user" + idx, idx);
            user.setAddressDataSet(new AddressDataSet("street" + idx));
            user.setPhoneDataSet(new PhoneDataSet("123456789" + idx));
            id.add(serviceUser.saveUser(user));
        }

        for (int idx = id.size() - 1; idx >= 0; idx--) {
            long startTime = System.nanoTime();
            User user = serviceUser.getUser(id.get(idx));
            long endTime = System.nanoTime();
            System.out.println("User receipt time = " + (endTime - startTime));
        }
    }
}
