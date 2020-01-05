package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.UserDao;
import ru.otus.api.model.AddressDataSet;
import ru.otus.api.model.PhoneDataSet;
import ru.otus.api.model.User;
import ru.otus.api.service.DBServiceUser;
import ru.otus.api.service.DBServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

public class DbServiceDemo {
  private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

  public static void main(String[] args) {
    // Все главное см в тестах
    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
            User.class, AddressDataSet.class, PhoneDataSet.class);

    SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    UserDao userDao = new UserDaoHibernate(sessionManager);
    DBServiceUser dbServiceUser = new DBServiceUserImpl(userDao);


    dbServiceUser.saveUser(new User(0, "Вася"));
    User mayBeCreatedUser = dbServiceUser.getUser(1);

    dbServiceUser.saveUser(new User(1L, "А! Нет. Это же совсем не Вася"));
    User mayBeUpdatedUser = dbServiceUser.getUser(1);

//    outputUserOptional("Created user", mayBeCreatedUser);
//    outputUserOptional("Updated user", mayBeUpdatedUser);
//  }
//
//  private static void outputUserOptional(String header, User mayBeUser) {
//    System.out.println("-----------------------------------------------------------");
//    System.out.println(header);
//    mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
  }
}
