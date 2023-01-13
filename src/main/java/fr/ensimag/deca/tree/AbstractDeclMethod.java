package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * TODO
 */
public abstract class AbstractDeclMethod extends Tree {

    /**
     * TODO
     * @param compiler
     * @param currentClass
     * @param superClass
     * @throws ContextualError
     */
    public abstract void verifyEnvMethod(DecacCompiler compiler, AbstractIdentifier currentClass, 
            AbstractIdentifier superClass) throws ContextualError;
            
}
