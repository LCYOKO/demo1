package com.xiaomi.web.servlet;

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
public class LogoutServlet extends HttpServlet {
    
    @Override
    public void doGet(Request request, Response response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/logout.html").forward(request,response);  
    }

    @Override
    public void doPost(Request request, Response response) throws ServletException, IOException {
        request.getSession().removeAttribute("username");
        request.getSession().invalidate();
        response.sendRedirect("/login");
    }
}
