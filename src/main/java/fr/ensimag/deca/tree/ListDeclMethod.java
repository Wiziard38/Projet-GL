package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * List of methods' declaration
 */
public class ListDeclMethod extends TreeList<AbstractMethod> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractMethod m : getList()) {
            m.decompile(s);
            s.println();
            s.println();
        }
    }

    /**
     * TODO
     */    
    public void verifyListDeclMethod(DecacCompiler compiler, AbstractIdentifier currentClass, 
            AbstractIdentifier superClass) throws ContextualError {
        
        for (AbstractMethod myMethod : this.getList()) {
            myMethod.verifyEnvMethod(compiler, currentClass, superClass);
            //myMethod.verifyEnv(compiler, currentClass);
        }
    }

}
