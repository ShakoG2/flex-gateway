package authgateway.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * Created by shako on 05/08/2021.
 */
@Configuration
public class RouterConfiguration {
    private final LoginHandler loginHandler;
//    private final LogOutHandler logOutHandler;

    public RouterConfiguration(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> jopaRouter() {
        return RouterFunctions
                .route(GET("/step1")
                        .and(accept(MediaType.APPLICATION_JSON)), loginHandler::step1).
                andRoute(GET("/secured/step2")
                        .and(accept(MediaType.APPLICATION_JSON)), loginHandler::step2);
    }
}
