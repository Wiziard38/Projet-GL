package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

import org.apache.commons.lang.Validate;

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

        if (operandType.isInt()) {
            this.setType(compiler.environmentType.INT);
            return compiler.environmentType.INT;
        }
        if (operandType.isFloat()) {
            this.setType(compiler.environmentType.FLOAT);
            return compiler.environmentType.FLOAT;
        }

        throw new ContextualError("Négation arithmétique sur un non-nombre", this.getLocation()); // Rule 3.37
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    protected void checkDecoration() {
        Validate.isTrue(this.getOperand().getType().isInt() || this.getOperand().getType().isFloat());
        Validate.isTrue(this.getType().sameType(this.getOperand().getType()));
    }

}
