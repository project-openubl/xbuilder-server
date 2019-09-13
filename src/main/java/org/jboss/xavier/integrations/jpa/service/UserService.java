package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.integrations.route.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class UserService
{
    @Inject
    AnalysisService analysisService;

    public User findUser(String username)
    {
        return new User(analysisService.countByOwner(username).intValue() == 0);
    }

}
