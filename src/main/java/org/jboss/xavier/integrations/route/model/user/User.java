package org.jboss.xavier.integrations.route.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User
{
    private boolean firstTimeCreatingReports;
}
