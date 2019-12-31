package org.openublpe.xmlbuilder.apisigner.resources;

import org.openublpe.xmlbuilder.apisigner.keys.KeyProvider;
import org.openublpe.xmlbuilder.apisigner.keys.KeyProviderFactory;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentFactory;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ProviderConfigProperty;
import org.openublpe.xmlbuilder.apisigner.models.utils.ModelToRepresentation;
import org.openublpe.xmlbuilder.apisigner.representations.idm.ComponentTypeRepresentation;
import org.openublpe.xmlbuilder.apisigner.representations.idm.ServerInfoRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
@Path("/server-info")
public class ServerInfoAdminResource {

    @Inject
    @Any
    private Instance<KeyProviderFactory> componentFactories;

    /**
     * General information about the server
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ServerInfoRepresentation getInfo() {
        ServerInfoRepresentation info = new ServerInfoRepresentation();
        setProviders(info);
        return info;
    }

    private void setProviders(ServerInfoRepresentation info) {
        info.setComponentTypes(new HashMap<>());

        List<ComponentTypeRepresentation> types = new ArrayList<>();

        for (ComponentFactory componentFactory : componentFactories) {
            ComponentTypeRepresentation rep = new ComponentTypeRepresentation();
            rep.setId(componentFactory.getId());
            rep.setHelpText(componentFactory.getHelpText());
            List<ProviderConfigProperty> configProperties = componentFactory.getConfigProperties();
            if (configProperties == null) {
                configProperties = Collections.emptyList();
            }
            rep.setProperties(ModelToRepresentation.toRepresentation(configProperties));

            types.add(rep);
        }

        // Workaround to not rely on UI
        info.getComponentTypes().put("keyProviders", types);
//        info.getComponentTypes().put(KeyProvider.class.getName(), types);
    }

}
