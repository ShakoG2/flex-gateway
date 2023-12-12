package authgateway.gateway;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public Keycloak Keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl("http://172.23.9.9/auth") // Replace with your Keycloak server URL
                .realm("Transguard") // Replace with your realm
                .clientId("tg-bls")
                .grantType("client_credentials")// Replace with your client ID
                .clientSecret("NgiBH9T4rgvcKZLumeDl5zhSEr4U6OSv") // Replace with your client secret if needed// Replace with admin password
                .build();
//        log.info(keycloak.realms().findAll().toString());
    }

}
