package customer.exception;

import lombok.Data;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
@Data
public class HandlerNotFoundException extends ServletException {
    private String method;
    private String uri;

    public HandlerNotFoundException(HttpServletRequest request) {
        this.method = request.getMethod();
        this.uri = request.getRequestURI();
    }
}
