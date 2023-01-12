package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ListDeclMethod extends TreeList<AbstractMethod> {

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub

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
