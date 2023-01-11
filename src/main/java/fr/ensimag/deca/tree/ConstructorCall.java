package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

public class ConstructorCall extends AbstractCall {

    private AbstractIdentifier method;
    private ListDeclParam arguments = new ListDeclParam();

    public ConstructorCall(AbstractIdentifier name, ListDeclParam args) {
        Validate.notNull(name);
        Validate.notNull(args);
        method = name;
        arguments = args;
    }

}
