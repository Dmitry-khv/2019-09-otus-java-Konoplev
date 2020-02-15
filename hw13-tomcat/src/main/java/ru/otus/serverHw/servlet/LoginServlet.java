package ru.otus.serverHw.servlet;

import ru.otus.serverHw.services.TemplateProcessor;
import ru.otus.serverHw.services.UserAuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_FAILED_RESULT = "failedResult";

    private static final int MAX_INACTIVE_INTERVAL = 30;
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";

    private final TemplateProcessor templateProcessor;
    private final UserAuthService userAuthService;

    public LoginServlet(TemplateProcessor templateProcessor, UserAuthService userAuthService) {
        this.templateProcessor = templateProcessor;
        this.userAuthService = userAuthService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getSession().isNew())
            resp.sendRedirect("/admin");

        Map<String, Object> params = new HashMap<>();
        params.put(PARAM_FAILED_RESULT, "");
        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, params));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(PARAM_LOGIN);
        String password = req.getParameter(PARAM_PASSWORD);

        if (userAuthService.authenticate(name, password)) {
            HttpSession httpSession = req.getSession();
            httpSession.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
            resp.sendRedirect("/admin");
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put(PARAM_FAILED_RESULT, "Неверный логин или пароль");
            resp.setContentType("text/html");
            resp.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, params));
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
