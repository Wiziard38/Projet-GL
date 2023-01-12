package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

public class MethodCall extends AbstractCall {

    private AbstractExpr expr;
    private ListExpr args = new ListExpr();

    public MethodCall(AbstractExpr e, AbstractIdentifier name, ListExpr args) {
        super(name);
        Validate.notNull(e);
        Validate.notNull(args);
        expr = e;
        this.args = args;
    }

}
