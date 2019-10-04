package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.integrations.route.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Component
public class UserService
{
    @Inject
    AnalysisService analysisService;

    @Value("${rest.authorization.administration}")
    private String[] authorizedAdminUsers = new String[0];

    public User findUser(String username)
    {
        return new User(analysisService.countByOwner(username).intValue() == 0);
    }

    public boolean isUserAllowedToAdministratorResources(String username) {
        return Arrays.asList(authorizedAdminUsers).contains(username);
    }

}
