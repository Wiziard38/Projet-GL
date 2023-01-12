package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Selection of a field
 */
public class Selection extends AbstractCall {

    private AbstractExpr expr;

    public Selection(AbstractExpr e, AbstractIdentifier name) {
        super(name);
        Validate.notNull(name);
        expr = e;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        getName().decompile(s);
    }

}
