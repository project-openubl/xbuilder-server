package org.openublpe.xmlbuilder.apisigner.representations.idm;

import java.util.List;
import java.util.Map;

public class ServerInfoRepresentation {

    private Map<String, List<ComponentTypeRepresentation>> componentTypes;

    public Map<String, List<ComponentTypeRepresentation>> getComponentTypes() {
        return componentTypes;
    }

    public void setComponentTypes(Map<String, List<ComponentTypeRepresentation>> componentTypes) {
        this.componentTypes = componentTypes;
    }
}
