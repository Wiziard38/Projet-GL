package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

/*
 * Call of a method function
 */
public class MethodCall extends AbstractCall {

    private AbstractExpr expr;

    public MethodCall(AbstractExpr e, AbstractIdentifier name, ListExpr args) {
        super(name, args);
        Validate.notNull(e);
        expr = e;
    }

}
