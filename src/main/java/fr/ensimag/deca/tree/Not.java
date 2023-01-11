package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.DecacInternalError;
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
                
        if (this.getOperand().verifyExpr(compiler, localEnv, currentClass).isBoolean()) {
            this.setType(compiler.environmentType.BOOLEAN);
            return compiler.environmentType.BOOLEAN;
        }

        throw new ContextualError(" Négation logique sur un non-boolean", this.getLocation()); // Rule 3.37
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }

    protected void checkDecoration() {
        super.checkDecoration();
        if (!this.getOperand().getType().isBoolean()) {
            throw new DecacInternalError("Not operand is not of Type boolean");
        }
        if (!this.getType().isBoolean()) {
            throw new DecacInternalError("Not is not of Type boolean");
        }
    }
}
