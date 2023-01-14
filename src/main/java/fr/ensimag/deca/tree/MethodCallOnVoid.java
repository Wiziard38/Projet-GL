package fr.ensimag.deca.tree;

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
        for (AbstractExpr e : getArgs().getList()) {
            e.decompile(s);
            s.print(" ");
        }
        s.print(")");
    }


    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        
        return super.verifyExprMessage(compiler, localEnv, currentClass, 
                "La sélection doit se faire sur une class"); // Rule 3.65 // Rule 3.66
    }
}
