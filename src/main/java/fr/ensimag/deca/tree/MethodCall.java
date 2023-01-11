package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

public class MethodCall extends AbstractCall {

    private AbstractExpr expr;
    private AbstractIdentifier method;
    private ListDeclParam arguments = new ListDeclParam();

    public MethodCall(AbstractExpr e, AbstractIdentifier name, ListDeclParam args) {
        Validate.notNull(e);
        Validate.notNull(name);
        Validate.notNull(args);
        expr = e;
        method = name;
        arguments = args;
    }

}
