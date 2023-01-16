package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Call of a method function on nothing
 */
public class MethodCallOnVoid extends AbstractCall {

    private ListExpr args;

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
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        return super.verifyExprMessage(compiler, localEnv, currentClass,
                "La sélection doit se faire sur une class"); // Rule 3.65 // Rule 3.66
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        getName().prettyPrint(s, prefix, false);
        args.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        getName().iter(f);
        args.iter(f);
    }
}
