package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

/*
 * Parameters declaration
 */
public abstract class AbstractDeclParam extends Tree {

    /**
     * Verify the param declaration for contextual error.
     * 
     * implements non-terminals "decl_param"
     * of [SyntaxeContextuelle] in pass 2
     * @param compiler
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_type" attribute)
     */
    public abstract Type verifySigParam(DecacCompiler compiler) throws ContextualError;

    /**
     * Verify the param declaration for contextual error.
     * 
     * implements non-terminals "decl_param"
     * of [SyntaxeContextuelle] in pass 3
     * @param compiler
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_type" attribute)
     * @param localEnv
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_exp" attribute)
     * @param paramIndex    Index that represent the position of the parameter in signature
     */
    public abstract void verifyEnvParam(DecacCompiler compiler, EnvironmentExp localEnv, int paramIndex)
            throws ContextualError;
    
}
