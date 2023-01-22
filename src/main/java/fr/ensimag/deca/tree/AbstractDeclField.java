package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;

public abstract class AbstractDeclField extends Tree {

    /**
     * Verify the field declaration for contextual error.
     * 
     * implements non-terminals "decl_field"
     * of [SyntaxeContextuelle] in pass 2
     * @param compiler
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_type" attribute)
     * @param currentClassDef
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_exp" attribute)
     */
    public abstract void verifyEnvField(DecacCompiler compiler, ClassDefinition currentClassDef) 
                    throws ContextualError;

    /**
     * Verify the field declaration for contextual error.
     * 
     * implements non-terminals "decl_field"
     * of [SyntaxeContextuelle] in pass 3
     * @param compiler
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_type" attribute)
     * @param currentClassDef
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_exp" attribute)
     */
    public abstract void verifyInitField(DecacCompiler compiler, ClassDefinition currentClassDef)
            throws ContextualError;

    protected abstract void codeGenDeclFiedl(DecacCompiler compiler, String name);
    public abstract AbstractIdentifier getName();
}
