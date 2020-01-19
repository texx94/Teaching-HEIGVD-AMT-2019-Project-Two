package ch.heigvd.amt.auth.api.filters;

import ch.heigvd.amt.auth.api.util.AuthUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class UserAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp,
                                    FilterChain chain) throws ServletException, IOException {
        String jwtToken = req.getHeader("Authorization");
        if (jwtToken == null) {
            resp.sendError(HttpStatus.UNAUTHORIZED.value(), "{\"Error\": \"The Authorization header must be set\"}");
            return;
        }

        Jws<Claims> jws = AuthUtils.decodeJWTString(jwtToken);
        if (jws == null) {
            resp.sendError(HttpStatus.UNAUTHORIZED.value(), "{\"Error\": \"Then token is not valid\"}");
            return;
        }

        req.setAttribute("userID", jws.getBody().get("userID", Long.class));
        req.setAttribute("isAdmin", jws.getBody().get("role", Boolean.class));
        chain.doFilter(req, resp);
    }
}
