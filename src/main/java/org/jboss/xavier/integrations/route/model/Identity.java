package org.jboss.xavier.integrations.route.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Identity
{
    String account_number;
    Map<String,String> internal = new HashMap<>();
    Optional<Map<String,String>> user = Optional.empty();
    Optional<String> type = Optional.empty();

    public void setType(String type)
    {
        this.type = Optional.of(type);
    }

    public String getType()
    {
         return type.orElse(null);
    }

    public void setUser(Map<String,String> user)
    {
        this.user = Optional.of(user);
    }

    public Map<String,String> getUser()
    {
        return user.orElse(null);
    }
}
