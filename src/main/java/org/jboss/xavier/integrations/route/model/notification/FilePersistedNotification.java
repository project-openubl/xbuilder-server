package org.jboss.xavier.integrations.route.model.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilePersistedNotification {
    String account;
    String rh_account;
    String principal;
    String payload_id;
    String request_id;
    String hash;
    Long size;
    String service;
    String category;
    String b64_identity;
    String url;
}
