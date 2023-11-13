//package authgateway.gateway.config;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
//import org.springframework.session.data.redis.ReactiveRedisSessionRepository;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RestController
//@Slf4j
//public class LogOutHandler {
////
////    private final ReactiveRedisSessionRepository sessionRepository;
////    private final ReactiveStringRedisTemplate redisTemplate;
//////    private final KafkaProducerService kafkaProducerService;
////
////    public LogOutHandler(ReactiveRedisSessionRepository sessionRepository, ReactiveStringRedisTemplate redisTemplate
//////                         KafkaProducerService kafkaProducerService
////    ) {
////        this.sessionRepository = sessionRepository;
////        this.redisTemplate = redisTemplate;
//////        this.kafkaProducerService = kafkaProducerService;
////    }
////
////    public Mono<ServerResponse> step1(ServerRequest serverRequest) {
////        log.info("step1 method execute!");
////        log.info(sessionRepository.getClass().toString());
////        String uri = serverRequest.uri().toString();
////
////
////        return serverRequest.bodyToMono(String.class)
////                .map(s -> {
////
////                    String logoutToken = s.split("=", 2)[1];
////                    log.info("lgoutToken {}", logoutToken);
////                    String[] splitString = logoutToken.split("\\.");
////                    log.info("splitString {}", splitString);
////                    String body = new String(Base64.getDecoder().decode(splitString[1]));
////                    log.info("body {}", body);
////
////                    ObjectMapper mapper = new ObjectMapper();
////                    HashMap bodyMap = null;
////                    try {
////                        bodyMap = mapper.readValue(body, HashMap.class);
////                        log.info("bodyMap {}", bodyMap);
////                    } catch (JsonProcessingException e) {
////                        throw new RuntimeException(e);
////                    }
////                    log.debug("logging out {}", bodyMap.get("sid"));
////
//////                    kafkaProducerService.sendMessage((String) bodyMap.get("sub"));
////
////                    return (String) bodyMap.get("sid");
////                })
////                .flatMap(this::findAndDeleteKeycloakSession)
////                .log("session invalidation")
////                .flatMapMany(webSessions -> {
////                    // For each web session ID in the set, delete the web session.
////                    log.info("webSessions" + webSessions);
////                    return Flux.fromIterable(webSessions)
////                            .flatMap(sessionRepository::deleteById);
////                })
////                .then(Mono.empty());
////    }
////
////    public Mono<Set<String>> findAndDeleteKeycloakSession(String keycloakSessionId) {
////        String redisKey = "keycloak:session:" + keycloakSessionId;
////        log.info("redis key  " + redisKey);
////
////        // Fetch all web sessions associated with the Keycloak session.
////        Flux<String> webSessionsFlux = redisTemplate.opsForSet().members(redisKey);
////
////        return webSessionsFlux.collect(Collectors.toSet()) // Convert the Flux to a Mono<Set<Object>>
////                .flatMap(webSessions -> {
////                    if (webSessions != null && !webSessions.isEmpty()) {
////                        log.info("get webSessions" + webSessions);
////
////                        // Delete the Keycloak session key from Redis.
////                        // The delete operation returns Mono<Boolean>,
////                        // but since we are not interested in the result, we transform it to just return the webSessions.
////                        return redisTemplate.delete(redisKey)
////                                .thenReturn(webSessions)
////                                .doOnSuccess(ws -> log.info("deleted with key"));
////                    } else {
////                        return Mono.empty();
////                    }
////                });
////    }
//}
