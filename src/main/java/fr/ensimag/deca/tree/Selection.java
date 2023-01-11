package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

public class Selection extends AbstractCall {

    private AbstractExpr expr;
    private AbstractIdentifier field;

    public Selection(AbstractExpr e, AbstractIdentifier name) {
        Validate.notNull(e);
        Validate.notNull(name);
        expr = e;
        field = name;
    }

}
