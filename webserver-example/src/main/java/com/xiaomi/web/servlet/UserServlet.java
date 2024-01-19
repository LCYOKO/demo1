package com.xiaomi.web.servlet;

import com.xiaomi.domain.User;
import com.xiaomi.service.UserService;
import com.xiaomi.web.core.exception.base.ServletException;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;
import com.xiaomi.web.core.servlet.impl.HttpServlet;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by SinjinSong on 2017/7/21.
 */
@Slf4j
public class UserServlet extends HttpServlet {
    private UserService userService;

    public UserServlet() {
        userService = UserService.getInstance();
    }
    
    @Override
    public void doGet(Request request, Response response) throws ServletException, IOException {
        User user = userService.findByUsername((String) request.getSession().getAttribute("username"));
        request.setAttribute("user",user);
        request.getRequestDispatcher("/views/user.html").forward(request, response);
    }
}
