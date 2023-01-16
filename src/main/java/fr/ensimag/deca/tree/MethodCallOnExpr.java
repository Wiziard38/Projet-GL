package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Call of a method function on an expression
 */
public class MethodCallOnExpr extends MethodCallOnVoid {

    private AbstractExpr expr;

    public MethodCallOnExpr(AbstractExpr e, AbstractIdentifier getFieldIdent, ListExpr args) {
        super(getFieldIdent, args);
        Validate.notNull(e);
        expr = e;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        getFieldIdent().decompile(s);
        s.print("(");
        int count = getArgs().size();
        for (AbstractExpr e : getArgs().getList()) {
            e.decompile(s);
            if (count != 1) {
                s.print(", ");
            }
            count--;
        }
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        getFieldIdent().prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        getFieldIdent().iter(f);
        getArgs().iter(f);
    }

}
