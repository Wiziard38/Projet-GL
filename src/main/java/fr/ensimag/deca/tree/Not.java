package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Non logique
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
        Validate.isTrue(this.getOperand().getType().isBoolean());
        Validate.isTrue(this.getType().sameType(this.getOperand().getType()));
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
    }

}
