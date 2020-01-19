package ch.heigvd.amt.movie.api.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRouter {
    @Bean
    public FilterRegistrationBean<UserAuthenticationFilter> userAUthenticationFilter() {
        FilterRegistrationBean<UserAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserAuthenticationFilter());
        registrationBean.addUrlPatterns("/moviesWatched/*");
        return registrationBean;
    }
}
