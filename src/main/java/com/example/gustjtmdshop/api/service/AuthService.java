package com.example.gustjtmdshop.api.service;

import com.example.gustjtmdshop.api.request.UserDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final Keycloak keycloak;

    public UserDto createUser(UserDto userDto) {

        // 유저정보 세팅
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDto.getUserName());
        user.setEmail(userDto.getEmail());

        // Get realm
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(user);
        if (response.getStatus() == 201) {

            String userId = CreatedResponseUtil.getCreatedId(response);

            // create password credential
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(userDto.getPassword());
            log.info("Created userId {}", userId);
            UserResource userResource = usersResource.get(userId);

            // Set password credential
            userResource.resetPassword(passwordCred);

            // role 세팅
            ClientRepresentation clientRep = realmResource.clients().findByClientId(clientId)
                    .get(0);
            RoleRepresentation clientRoleRep = realmResource.clients().get(clientRep.getId())
                    .roles().get(userDto.getUserRole().getCode()).toRepresentation();
            userResource.roles().clientLevel(clientRep.getId()).add(Arrays.asList(clientRoleRep));

            GroupRepresentation groupRep = realmResource.groups().groups().stream()
                    .filter(group -> group.getName().equals("admin"))
                    .findFirst()
                    .orElse(null);

            if (groupRep != null) {
                // Add user to the group
                userResource.joinGroup(groupRep.getId());
            }

        }

        userDto.setStatus(response.getStatus());
        userDto.setStatusInfo(response.getStatusInfo().toString());

        return userDto;
    }

    public AccessTokenResponse setAuth(UserDto userDto) {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("grant_type", "password");

        Configuration configuration =
                new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);

        AccessTokenResponse response =
                authzClient.obtainAccessToken(userDto.getUserName(), userDto.getPassword());

        return response;
    }

    /*
     *  사용자 존재하는지 체크
     * */
    public boolean existsByUsername(String userName) {

        List<UserRepresentation> search = keycloak.realm(realm).users()
                .search(userName);
        if (search.size() > 0) {
            log.debug("search : {}", search.get(0).getUsername());
            return true;
        }
        return false;
    }
}

