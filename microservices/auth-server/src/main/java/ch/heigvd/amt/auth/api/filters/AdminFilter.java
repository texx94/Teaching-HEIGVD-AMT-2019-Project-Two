package ch.heigvd.amt.auth.api.filters;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(2)
public class AdminFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp,
                                    FilterChain chain) throws ServletException, IOException {
        boolean isAdmin = (Boolean) req.getAttribute("isAdmin");

        if (!isAdmin) {
            resp.sendError(HttpStatus.UNAUTHORIZED.value(), "{\"Error\": \"You must be an admin\"}");
            return;
        }

        req.setAttribute("isAdmin", true);
        chain.doFilter(req, resp);
    }
}
