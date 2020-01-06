package ru.otus.hibernate.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.api.dao.UserDao;
import ru.otus.api.model.AddressDataSet;
import ru.otus.api.model.PhoneDataSet;
import ru.otus.api.model.User;
import ru.otus.api.service.DBServiceUser;
import ru.otus.api.service.DBServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с User должен уметь ")
class UserDaoHibernateTest {
    private static final String HIBERNATE_CONFIG = "hibernate-test.cfg.xml";
    private static final long USER_ID = 1L;

    private User user;
    private User simpleUser;
    private AddressDataSet address;
    private PhoneDataSet phone;

    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManager;
    private UserDao userDao;
    private DBServiceUser serviceUser;


    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CONFIG, User.class, AddressDataSet.class, PhoneDataSet.class);
        sessionManager = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManager);
        serviceUser = new DBServiceUserImpl(userDao);

        simpleUser = new User();
        user = new User(0,"Ivan", 15);
        address = new AddressDataSet("New-York");
        user.setAddressDataSet(address);
        for(int i = 0; i < 5; i++)
            user.setPhoneDataSet(new PhoneDataSet("300-200-10" + i));

    }

    @Test
    @DisplayName("корректно сохранять и загружать пользователя")
    public void shouldCorrectSaveAndLoadUser(){
        long id = serviceUser.saveUser(user);
        assertThat(id).isEqualTo(USER_ID);

        User mayBeUser = serviceUser.getUser(id);
        assertThat(user).isEqualToComparingFieldByField(mayBeUser);
        System.out.println(user);
        System.out.println(mayBeUser);
    }

    @Test
    @DisplayName("корректно сохранять и загружать пользователя без связей с другими сущностями")
    public void shouldSaveAndUpdateSimpleUser() {
        long id = serviceUser.saveUser(simpleUser);
        assertThat(id).isEqualTo(USER_ID);

        User mayBeUser = serviceUser.getUser(id);
        assertThat(simpleUser).isEqualToComparingFieldByField(mayBeUser);
        System.out.println(simpleUser);
        System.out.println(mayBeUser);
    }

    @Test
    @DisplayName("корректно менять имя и возраст у существующего пользователя")
    public void shouldCorrectUpdateUserName() {
        long id = serviceUser.saveUser(user);
        System.out.println(user);

        user.setName("Vasya");
        user.setAge(20);
        serviceUser.saveUser(user);
        User maybeUser = serviceUser.getUser(id);
        assertThat(maybeUser).isNotNull().isEqualToComparingFieldByField(user);

        System.out.println(user);
        System.out.println(maybeUser);
    }

    @Test
    @DisplayName("корректно менять адресс у существующего пользователя")
    public void shouldCorrectUpdateUserAddress() {
        long id = serviceUser.saveUser(user);
        AddressDataSet newAddress = new AddressDataSet("Vladivostok");
        user.setAddressDataSet(newAddress);
        serviceUser.saveUser(user);
        User maybeUser = serviceUser.getUser(id);
        assertThat(maybeUser).isNotNull().isEqualToComparingFieldByField(user);

        System.out.println(user);
        System.out.println(maybeUser);
    }

    @Test
    @DisplayName("корректно менять телефон у существующего пользователя")
    public void shouldCorrectUpdateUserPhoneSet() {
        long id = serviceUser.saveUser(user);
        user.setPhoneDataSet(new PhoneDataSet("123-123-123"));
        serviceUser.saveUser(user);
        User maybeUser = serviceUser.getUser(id);
        assertThat(maybeUser).isNotNull().isEqualToComparingFieldByField(user);

        System.out.println(user);
        System.out.println(maybeUser);
    }
}