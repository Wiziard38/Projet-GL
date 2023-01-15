package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Call of a method function on an expression
 */
public class MethodCallOnExpr extends MethodCallOnVoid {

    private AbstractExpr expr;

    public MethodCallOnExpr(AbstractExpr e, AbstractIdentifier name, ListExpr args) {
        super(name, args);
        Validate.notNull(e);
        expr = e;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        getName().decompile(s);
        s.print("(");
        for (AbstractExpr e : getArgs().getList()) {
            e.decompile(s);
            s.print(" ");
        }
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        getName().prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        getName().iter(f);
        getArgs().iter(f);
    }

}
