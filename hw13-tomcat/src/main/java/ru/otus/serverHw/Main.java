package ru.otus.serverHw;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.SessionFactory;
import ru.otus.cache.api.dao.UserDao;
import ru.otus.cache.api.model.AddressDataSet;
import ru.otus.cache.api.model.PhoneDataSet;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.service.DBServiceUser;
import ru.otus.cache.api.service.DBServiceUserImpl;
import ru.otus.cache.hibernate.HibernateUtils;
import ru.otus.cache.hibernate.repository.UserDaoHibernate;
import ru.otus.cache.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.serverHw.services.*;
import ru.otus.serverHw.servlet.AdminServlet;
import ru.otus.serverHw.servlet.AuthorizationFilter;
import ru.otus.serverHw.servlet.LoginServlet;

import java.util.Optional;
import java.util.stream.IntStream;

public class Main {
    private static final String HIBERNATE_CONFIG = "hibernate.cfg.xml";
    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManager;
    private UserDao userDao;
    private DBServiceUser serviceUser;

    private static final int PORT = 8080;
    private static final String TEMPLATE_DIR = "/templates/";
    private static final String STATIC_DIR = "static";
    private static final String START_PAGE_NAME = "index.html";


    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.daoInit();
        main.serverInit();
    }

    public void serverInit() throws Exception {
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATE_DIR);

        UserAuthService userAuthService = new UserAuthServiceImpl(userDao);
        HandlerList handlers = new HandlerList();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[] {START_PAGE_NAME});
        resourceHandler.setResourceBase(staticDirPath(STATIC_DIR));
        handlers.addHandler(resourceHandler);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.addServlet(new ServletHolder(new UsersServlet(userDao, templateProcessor)), "/users");
//        context.addServlet(new ServletHolder(new LoginServlet(templateProcessor, userAuthService)),"/login");
        context.addServlet(new ServletHolder(new AdminServlet(templateProcessor, serviceUser)), "/admin");
        handlers.addHandler(applyFilters(context, templateProcessor, userAuthService, "/admin"));

        Server server = new Server(PORT);
        server.setHandler(handlers);
        server.start();
        server.join();
    }

    public void daoInit() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CONFIG, User.class, AddressDataSet.class, PhoneDataSet.class);
        sessionManager = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManager);
        serviceUser = new DBServiceUserImpl(userDao);

        User admin = new User(1, "dmitry", 31, "admin", "11111", User.ROLE.ADMIN);
        admin.setAddressDataSet(new AddressDataSet("Lomonosova str."));
        admin.setPhoneDataSet(new PhoneDataSet("999999"));
        serviceUser.saveUser(admin);
    }

    public String staticDirPath(String fileOrResourceName) {
        return Optional.ofNullable(FileSystemHelper.class.getClassLoader().getResource(fileOrResourceName))
                .orElseThrow(() -> new RuntimeException(String.format("File \"%s\" not found", fileOrResourceName))).toExternalForm();
    }

    public ServletContextHandler applyFilters(ServletContextHandler servletContextHandler, TemplateProcessor templateProcessor,
                                              UserAuthService userAuthService, String...path) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, userAuthService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        IntStream.range(0, path.length)
                .forEachOrdered(i -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path[i], null));
        return servletContextHandler;
    }
}
