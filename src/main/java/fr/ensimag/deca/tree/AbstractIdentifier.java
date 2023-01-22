package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.SymbolTable;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractIdentifier extends AbstractLValue {
    private static final Logger LOG = Logger.getLogger(AbstractIdentifier.class);

    @Override
    protected void checkDecoration() {
        LOG.debug(this + this.getLocation().toString());
        Validate.notNull(this.getDefinition());
    }
    
    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *                            if the definition is not a class definition.
     */
    public abstract ClassDefinition getClassDefinition();

    public abstract Definition getDefinition();

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *                            if the definition is not a field definition.
     */
    public abstract FieldDefinition getFieldDefinition();

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *                            if the definition is not a method definition.
     */
    public abstract MethodDefinition getMethodDefinition();

    public abstract SymbolTable.Symbol getName();

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ExpDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *                            if the definition is not a field definition.
     */
    public abstract ExpDefinition getExpDefinition();

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *                            if the definition is not a field definition.
     */
    public abstract VariableDefinition getVariableDefinition();

    public abstract void setDefinition(Definition definition);

    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     * 
     * @param compiler  contains "env_types" attribute
     * @param checkVoid if true, verifies that the returned type is not Void
     * @param message   if checkVoid is true, specifies the message to be displayed if
     *                  the Type is indeed Void
     * @return the type corresponding to this identifier
     *         (corresponds to the "type" attribute)
     */
    public abstract Type verifyType(DecacCompiler compiler, boolean checkVoid,
            String message) throws ContextualError;

    /**
     * Verify the ident declaration for contextual error.
     * 
     * implements non-terminals "ident"
     * of [SyntaxeContextuelle] in pass 3
     * @param compiler
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_type" attribute)
     * @param localEnv
     *                      Environment in which the expression should be checked
     *                      (corresponds to the "env_exp" attribute)
     * @return              
     *                      The Definition corresponding to this identifier
     *                      (corresponds to the "definition" attribute)
     */
    public abstract Definition verifyDefinition(DecacCompiler compiler, EnvironmentExp localEnv)
            throws ContextualError;
}
