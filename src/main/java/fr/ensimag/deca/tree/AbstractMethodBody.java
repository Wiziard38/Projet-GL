package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

/**
 * Method body declaration.
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractMethodBody extends Tree {

    /**
     * Verify the method body for contextual error.
     * 
     * implements non-terminals "bloc"
     * of [SyntaxeContextuelle] in pass 3
     * 
     * @param compiler
     *                        Environment in which the expression should be checked
     *                        (corresponds to the "env_type" attribute)
     * @param paramsEnvExp
     *                        Environment in which the expression should be checked
     *                        (corresponds to the "env_exp_param" attribute)
     * @param currentClassDef
     *                        Environment in which the expression should be checked
     *                        (corresponds to the "env_exp" attribute)
     */
    public abstract void verifyBody(DecacCompiler compiler, EnvironmentExp paramsEnvExp,
            ClassDefinition currentClassDef, Type returnType) throws ContextualError;

    protected abstract void codeGenInstBody(DecacCompiler compiler, String name);
}
