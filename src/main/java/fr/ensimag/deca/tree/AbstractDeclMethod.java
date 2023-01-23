package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;

/**
 * TODO
 */
public abstract class AbstractDeclMethod extends Tree {

    /**
     * Verify the method declaration for contextual error.
     * 
     * implements non-terminals "decl_method"
     * of [SyntaxeContextuelle] in pass 2
     * @param compiler
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_type" attribute)
     * @param currentClassDef
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_exp" attribute)
     */
    public abstract void verifyEnvMethod(DecacCompiler compiler, ClassDefinition currentClassDef,
            AbstractIdentifier superClass) throws ContextualError;

    /**
     * Verify the method declaration for contextual error.
     * 
     * implements non-terminals "decl_method"
     * of [SyntaxeContextuelle] in pass 3
     * @param compiler
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_type" attribute)
     * @param currentClassDef
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_exp" attribute)
     */
    public abstract void verifyBodyMethod(DecacCompiler compiler, ClassDefinition currentClassDef)
            throws ContextualError;

    public abstract AbstractIdentifier getName();

    protected abstract void codeGenCorpMethod(DecacCompiler compiler, String name);

}
