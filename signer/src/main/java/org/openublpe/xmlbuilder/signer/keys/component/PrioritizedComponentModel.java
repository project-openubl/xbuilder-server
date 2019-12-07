package org.openublpe.xmlbuilder.signer.keys.component;

import java.util.Comparator;

public class PrioritizedComponentModel extends ComponentModel {

    public static final String PRIORITY = "priority";

    public static final Comparator<ComponentModel> comparator = new Comparator<ComponentModel>() {
        @Override
        public int compare(ComponentModel o1, ComponentModel o2) {
            return parsePriority(o1) - parsePriority(o2);
        }
    };

    public PrioritizedComponentModel(ComponentModel copy) {
        super(copy);
    }

    public PrioritizedComponentModel() {
    }

    public static int parsePriority(ComponentModel component) {
        String priority = component.getConfig().getFirst(PRIORITY);
        if (priority == null) return 0;
        return Integer.valueOf(priority);
    }

    public int getPriority() {
        return parsePriority(this);
    }

    public void setPriority(int priority) {
        getConfig().putSingle("priority", Integer.toString(priority));
    }
}
