package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.integrations.route.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserService
{
    // TODO remove it once persistence in the DB will be implemented
    @Value("${rest.user.value}")
    private boolean firstTime;

    public User findUser()
    {
        return User.builder().firstTimeCreatingReports(firstTime).build();
    }

}
