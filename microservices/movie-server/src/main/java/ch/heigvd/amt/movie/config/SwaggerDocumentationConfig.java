package ch.heigvd.amt.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Movie API")
                .description("An API that allows you to keep a history of the movies you've watched")
                .license("MIT")
                .licenseUrl("http://opensource.org/licenses/MIT")
                .version("0.1.0")
                .contact(new Contact("MovieAPI team", "", "mateo.tutic@heig-vd.ch"))
                .build();
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ch.heigvd.amt.movie.api"))
                .build()
                .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
                .apiInfo(apiInfo())
                .tags(new Tag("movies", "Get the list of movies and create, update or delete a new movie"),
                        new Tag("users", "Get the list of users and create, update or delete a new user"),
                        new Tag("moviesWatched", "Get the list of watched movies and create, update or delete a new movie"))
                .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", "header")))
                .securityContexts(Collections.singletonList(
                        SecurityContext.builder()
                                .securityReferences(
                                        Collections.singletonList(SecurityReference.builder()
                                                .reference("JWT")
                                                .scopes(new AuthorizationScope[0])
                                                .build())
                                )
                                .build())
                );
    }

}