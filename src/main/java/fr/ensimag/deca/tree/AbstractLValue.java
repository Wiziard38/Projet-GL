package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;

/**
 * Left-hand side value of an assignment.
 * 
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractLValue extends AbstractExpr {

    public abstract void verifyLValue(EnvironmentExp localEnv) throws ContextualError;

    public abstract ExpDefinition getExpDefinition();
}
