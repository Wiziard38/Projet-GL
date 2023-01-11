package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import org.apache.commons.lang.Validate;

/*
 * Call a constructor
 */
public class ConstructorCall extends AbstractCall {

    private ListExpr args = new ListExpr();

    public ConstructorCall(AbstractIdentifier name, ListExpr args) {
        super(name);
        Validate.notNull(args);
        this.args = args;
    }

}
