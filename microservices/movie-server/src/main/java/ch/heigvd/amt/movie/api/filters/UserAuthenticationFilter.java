package ch.heigvd.amt.movie.api.filters;

import ch.heigvd.amt.movie.api.util.AuthUtils;
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
            resp.sendError(HttpStatus.UNAUTHORIZED.value(), "The Authorization header must be set");
            return;
        }

        Jws<Claims> jws = AuthUtils.decodeJWTString(jwtToken);
        if (jws == null) {
            resp.sendError(HttpStatus.UNAUTHORIZED.value(), "Then token is not valid");
            return;
        }

        req.setAttribute("userID", jws.getBody().get("userID", Long.class));
        req.setAttribute("isAdmin", jws.getBody().get("role", Boolean.class));
        chain.doFilter(req, resp);
    }
}
