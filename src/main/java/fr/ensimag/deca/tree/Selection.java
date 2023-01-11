package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

public class Selection extends AbstractCall {

    private AbstractExpr expr;

    public Selection(AbstractExpr e, AbstractIdentifier name) {
        super(name);
        Validate.notNull(name);
        expr = e;
    }

}
