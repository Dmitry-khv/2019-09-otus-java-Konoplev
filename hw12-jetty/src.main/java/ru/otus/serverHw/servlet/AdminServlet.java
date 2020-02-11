package ru.otus.serverHw.servlet;

import ru.otus.cache.api.model.AddressDataSet;
import ru.otus.cache.api.model.PhoneDataSet;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.service.DBServiceUser;
import ru.otus.serverHw.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String CREATED_USER_PARAM = "createdUser";
    private static final String USERS_LIST_PARAM = "usersList";
    private final TemplateProcessor templateProcessor;
    private final DBServiceUser serviceUser;

    public AdminServlet(TemplateProcessor templateProcessor, DBServiceUser serviceUser) throws IOException {
        this.templateProcessor = templateProcessor;
        this.serviceUser = serviceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageParams = new HashMap<>();
        pageParams.put(USERS_LIST_PARAM, Collections.emptyList());
        pageParams.put(CREATED_USER_PARAM, Collections.emptyList());

        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageParams));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         // получить список пользователей
        if(req.getParameter("getUsers") != null) {
            Map<String, Object> params = new HashMap<>();
            params.put(USERS_LIST_PARAM, serviceUser.getUserList());

            resp.setContentType("text/html");
            resp.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, params));
            resp.setStatus(HttpServletResponse.SC_OK);
        } if (req.getParameter("addUser") != null) {
            User createdUser = new User();
            createdUser.setName(req.getParameter("name"));
            createdUser.setAge(Integer.parseInt(req.getParameter("age")));
            createdUser.setLogin(req.getParameter("login"));
            createdUser.setPassword(req.getParameter("password"));
            if (req.getParameter("address") != null)
                createdUser.setAddressDataSet(new AddressDataSet(req.getParameter("address")));
            if (req.getParameter("phone") != null)
                createdUser.setPhoneDataSet(new PhoneDataSet(req.getParameter("phone")));
            if(req.getParameter("role").equals("admin"))
                createdUser.setRole(User.ROLE.ADMIN);
            if(req.getParameter("role").equals("user"))
                createdUser.setRole(User.ROLE.USER);

            serviceUser.saveUser(createdUser);

            Map<String, Object> params = new HashMap<>();
            params.put(CREATED_USER_PARAM, createdUser);
            params.put(USERS_LIST_PARAM, Collections.emptyList());

            resp.setContentType("text/html");
            resp.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, params));
            resp.setStatus(HttpServletResponse.SC_OK);
        } if(req.getParameter("exit") != null) {
            req.getSession().invalidate();
            resp.sendRedirect("/login");
        }
    }
}
