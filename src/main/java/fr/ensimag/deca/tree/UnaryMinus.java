package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * @author gl39
 * @date 01/01/2023
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type operandType = this.getOperand().verifyExpr(compiler, localEnv, currentClass);

        if (operandType == compiler.environmentType.INT) {
            this.setType(compiler.environmentType.INT);
            return compiler.environmentType.INT;
        }
        if (operandType == compiler.environmentType.FLOAT) {
            this.setType(compiler.environmentType.FLOAT);
            return compiler.environmentType.FLOAT;
        }

        throw new ContextualError("Applying '-' not on a number", this.getLocation());
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

}
