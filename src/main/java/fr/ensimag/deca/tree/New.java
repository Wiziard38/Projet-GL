package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Call a constructor via keyword 'new'
 */
public class New extends AbstractCall {

    public New(AbstractIdentifier name) {
        super(name);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        getName().decompile(s);
        s.print("();");
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        
        return super.verifyExprMessage(compiler, localEnv, currentClass, 
                "'New' ne peut etre affecté que pour une class");
    }
}
