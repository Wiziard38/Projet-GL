package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

public class ConstructorCall extends AbstractCall {

    private AbstractIdentifier method;
    private ListExpr arguments = new ListExpr();

    public ConstructorCall(AbstractIdentifier name, ListExpr args) {
        Validate.notNull(name);
        Validate.notNull(args);
        method = name;
        arguments = args;
    }

}
