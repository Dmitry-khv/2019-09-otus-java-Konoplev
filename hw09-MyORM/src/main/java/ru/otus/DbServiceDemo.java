package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.ModelDao;
import ru.otus.api.model.User;
import ru.otus.api.service.DBServiceModel;
import ru.otus.api.service.DbServiceModelImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.h2.TableCreator;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.dao.ModelDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;


public class DbServiceDemo {
  private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

  public static void main(String[] args) throws Exception {
    DataSource dataSource = new DataSourceH2();
//    DbServiceDemo demo = new DbServiceDemo();

//    demo.createTable(dataSource);
    new TableCreator(User.class, dataSource).createTables();

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutor<User> dbExecutor = new DbExecutor<>();
//    ModelDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);
//    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
//    long id = dbServiceUser.saveUser(new User(0, "dbServiceUser"));
//    Optional<User> user = dbServiceUser.getUser(id);

    ModelDaoJdbc modelDao = new ModelDaoJdbc<>(sessionManager, dbExecutor);
    DbServiceModelImpl dbServiceModel = new DbServiceModelImpl<>(modelDao);
    dbServiceModel.create(new User(1, "Pit",  60));
    dbServiceModel.create(new User(2, "Pot",  40));

//    System.out.println(user);
//    user.ifPresentOrElse(
//        crUser -> logger.info("created user, name:{}", crUser.getName()),
//        () -> logger.info("user was not created")
//    );

  }
}
