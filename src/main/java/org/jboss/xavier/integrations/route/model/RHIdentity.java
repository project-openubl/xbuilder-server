package org.jboss.xavier.integrations.route.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RHIdentity {
    String account_number;
    Map<String,String> internal;


}
