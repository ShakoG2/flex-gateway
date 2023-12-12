package authgateway.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("password-change")
@RequiredArgsConstructor
public class PasswordChangeController {

    private final KeycloakService keycloakService; // Service to interact with Keycloak


    @PostMapping
    public void makePasswordChange(@RequestParam String password) {
        String userId = "2e5aea75-fad9-41f4-b2c6-880fec13602c";
        keycloakService.changeUserPassword(userId, password);
    }

}
