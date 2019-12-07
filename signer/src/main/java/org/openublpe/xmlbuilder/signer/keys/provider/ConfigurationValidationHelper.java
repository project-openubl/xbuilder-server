package org.openublpe.xmlbuilder.signer.keys.provider;

import org.openublpe.xmlbuilder.signer.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.signer.keys.component.ComponentValidationException;

import java.util.List;

public class ConfigurationValidationHelper {

    private ComponentModel model;

    private ConfigurationValidationHelper(ComponentModel model) {
        this.model = model;
    }

    public static ConfigurationValidationHelper check(ComponentModel model) {
        return new ConfigurationValidationHelper(model);
    }

    public ConfigurationValidationHelper checkInt(ProviderConfigProperty property, boolean required) throws ComponentValidationException {
        return checkInt(property.getName(), property.getLabel(), required);
    }

    public ConfigurationValidationHelper checkList(ProviderConfigProperty property, boolean required) throws ComponentValidationException {
        checkSingle(property.getName(), property.getLabel(), required);

        String value = model.getConfig().getFirst(property.getName());
        if (value != null && !property.getOptions().contains(value)) {
            StringBuilder options = new StringBuilder();
            int i = 1;
            for (String o : property.getOptions()) {
                if (i == property.getOptions().size()) {
                    options.append(" or ");
                } else if (i > 1) {
                    options.append(", ");
                }
                options.append(o);
                i++;
            }
            throw new ComponentValidationException("''{0}'' should be {1}", property.getLabel(), options.toString());
        }

        return this;
    }

    public ConfigurationValidationHelper checkInt(String key, String label, boolean required) throws ComponentValidationException {
        checkSingle(key, label, required);

        String val = model.getConfig().getFirst(key);
        if (val != null) {
            try {
                Integer.parseInt(val);
            } catch (NumberFormatException e) {
                throw new ComponentValidationException("''{0}'' should be a number", label);
            }
        }

        return this;
    }

    public ConfigurationValidationHelper checkLong(ProviderConfigProperty property, boolean required) throws ComponentValidationException {
        return checkLong(property.getName(), property.getLabel(), required);
    }

    public ConfigurationValidationHelper checkLong(String key, String label, boolean required) throws ComponentValidationException {
        checkSingle(key, label, required);

        String val = model.getConfig().getFirst(key);
        if (val != null) {
            try {
                Long.parseLong(val);
            } catch (NumberFormatException e) {
                throw new ComponentValidationException("''{0}'' should be a number", label);
            }
        }

        return this;
    }

    public ConfigurationValidationHelper checkSingle(ProviderConfigProperty property, boolean required) throws ComponentValidationException {
        return checkSingle(property.getName(), property.getLabel(), required);
    }

    public ConfigurationValidationHelper checkSingle(String key, String label, boolean required) throws ComponentValidationException {
        if (model.getConfig().containsKey(key) && model.getConfig().get(key).size() > 1) {
            throw new ComponentValidationException("''{0}'' should be a single entry", label);
        }

        if (required) {
            checkRequired(key, label);
        }

        return this;
    }

    public ConfigurationValidationHelper checkRequired(ProviderConfigProperty property) throws ComponentValidationException {
        return checkRequired(property.getName(), property.getLabel());
    }

    public ConfigurationValidationHelper checkRequired(String key, String label) throws ComponentValidationException {
        List<String> values = model.getConfig().get(key);
        if (values == null) {
            throw new ComponentValidationException("''{0}'' is required", label);
        }

        return this;
    }

    public ConfigurationValidationHelper checkBoolean(ProviderConfigProperty property, boolean required) throws ComponentValidationException {
        return checkBoolean(property.getName(), property.getLabel(), required);
    }

    public ConfigurationValidationHelper checkBoolean(String key, String label, boolean required) {
        checkSingle(key, label, required);

        String val = model.getConfig().getFirst(key);
        if (val != null && !(val.equals("true") || val.equals("false"))) {
            throw new ComponentValidationException("''{0}'' should be ''true'' or ''false''", label);
        }

        return this;
    }

}
