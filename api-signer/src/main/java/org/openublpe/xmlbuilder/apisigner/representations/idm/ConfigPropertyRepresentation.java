package org.openublpe.xmlbuilder.apisigner.representations.idm;

import java.util.List;

public class ConfigPropertyRepresentation {
    private String name;
    private String label;
    private String helpText;
    private String type;
    private Object defaultValue;
    private List<String> options;
    private boolean secret;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }
}
