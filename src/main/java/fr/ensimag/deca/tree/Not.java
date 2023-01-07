package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                
        if (this.getOperand().verifyExpr(compiler, localEnv, currentClass) == compiler.environmentType.BOOLEAN) {
            return compiler.environmentType.BOOLEAN;
        }

        throw new ContextualError("Applying 'not' on a non-boolean", this.getLocation());
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }
}
