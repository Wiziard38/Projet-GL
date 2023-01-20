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
     * TODO
     * 
     * @param compiler
     * @return
     * @throws ContextualError
     */
    public abstract Type verifySigParam(DecacCompiler compiler) throws ContextualError;

    /**
     * TODO
     * 
     * @param compiler
     * @return
     * @throws ContextualError
     */
    public abstract void verifyEnvParam(DecacCompiler compiler, EnvironmentExp localEnv, int paramIndex)
            throws ContextualError;
    
}
