package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
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

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        
        return super.verifyExprMessage(compiler, localEnv, currentClass, 
                "La sélection doit se faire sur une class"); // Rule 3.65 // Rule 3.66
    }
    

}
