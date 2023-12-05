package authgateway.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.DelegatingServerAuthenticationEntryPoint;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {


    private ServerWebExchangeMatcher exchangeMatcher() {
        return (exchange) -> {
            List<String> acceptHeaders = exchange.getRequest().getHeaders().getOrEmpty(HttpHeaders.ACCEPT);
            List<String> contentTypeHeaders = exchange.getRequest().getHeaders().getOrEmpty(HttpHeaders.CONTENT_TYPE);
            for (String headerValue : acceptHeaders) {
                if (headerValue.contains("application/json")) {
                    log.info("headerValue.contains(application/json)   " + headerValue.contains("application/json"));
                    return ServerWebExchangeMatcher.MatchResult.match();
                }
            }
            for (String headerValue : contentTypeHeaders) {
                if (headerValue.contains("application/json")) {
                    log.info("headerValue.contains(application/json)   " + headerValue.contains("application/json"));
                    return ServerWebExchangeMatcher.MatchResult.match();
                }
            }
            return ServerWebExchangeMatcher.MatchResult.notMatch();
        };
    }


    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

        ServerWebExchangeMatcher xhrMatcher = exchangeMatcher();

        DelegatingServerAuthenticationEntryPoint.DelegateEntry entryPoints =
                new DelegatingServerAuthenticationEntryPoint.DelegateEntry(xhrMatcher, new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED));

        DelegatingServerAuthenticationEntryPoint nonAjaxLoginEntryPoint = new DelegatingServerAuthenticationEntryPoint(entryPoints);


        nonAjaxLoginEntryPoint.setDefaultEntryPoint(new RedirectServerAuthenticationEntryPoint("/oauth2/authorization/keycloak"));

        ServerRequestCache requestCache = new WebSessionServerRequestCache();


        RedirectServerAuthenticationSuccessHandler redirectServerAuthenticationSuccessHandler = new RedirectServerAuthenticationSuccessHandler();
        redirectServerAuthenticationSuccessHandler.setRequestCache(requestCache);

        redirectServerAuthenticationSuccessHandler.setRequestCache(requestCache);

        http
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint(nonAjaxLoginEntryPoint))
                .headers()
                .frameOptions()
                .disable()
                .and()
                .csrf()
                .disable()
                .requestCache(requestCacheSpec -> requestCacheSpec.requestCache(requestCache))
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .anyExchange().authenticated()
                        .and()
                        .oauth2ResourceServer()
                        .jwt()
                ).oauth2Login()
                .and()
                .logout()
                .logoutUrl("/logout");

        return http.build();
    }

    @Bean
    public WebSessionIdResolver webSessionIdResolver() {
        CookieWebSessionIdResolver resolver = new CookieWebSessionIdResolver();
        resolver.setCookieName("SESL");
        resolver.addCookieInitializer((builder) -> builder.path("/"));
        resolver.addCookieInitializer((builder) -> builder.sameSite("Lax"));
        resolver.addCookieInitializer((builder) -> builder.domain(""));
        return resolver;
    }


}
