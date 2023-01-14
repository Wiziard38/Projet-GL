package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
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
    public abstract void verifyEnvMethod(DecacCompiler compiler, ClassDefinition currentClassDef, 
            AbstractIdentifier superClass) throws ContextualError;
          
    
    /**
     * TODO
     * @param compiler
     * @param currentClass
     * @param superClass
     * @throws ContextualError
     */
    public abstract void verifyBodyMethod(DecacCompiler compiler, ClassDefinition currentClassDef)
            throws ContextualError;

}
