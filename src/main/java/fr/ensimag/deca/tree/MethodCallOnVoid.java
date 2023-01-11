package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Call of a method function on nothing
 */
public class MethodCallOnVoid extends AbstractCall {

    private ListExpr args = new ListExpr();

    public MethodCallOnVoid(AbstractIdentifier name, ListExpr args) {
        super(name);
        Validate.notNull(args);
        this.args = args;
    }

    public ListExpr getArgs() {
        return args;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getName().decompile(s);
        s.print("(");
        for (AbstractExpr e : getArgs().getList()) {
            e.decompile(s);
            s.print(" ");
        }
        s.print(")");
    }

}
