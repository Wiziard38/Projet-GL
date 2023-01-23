package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Modulo operation
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        if ((this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass).isInt())
                && (this.getRightOperand().verifyExpr(compiler, localEnv, currentClass).isInt())) {
            this.setType(compiler.environmentType.INT);
            return compiler.environmentType.INT;
        }
        throw new ContextualError("Tentative de modulo sur des non-entiers", this.getLocation()); // Rule 3.33
    }

    @Override
    protected String getOperatorName() {
        return "%";
    }

    @Override
    protected void checkDecoration() {
        Validate.isTrue(this.getType().sameType(this.getRightOperand().getType()));
        Validate.isTrue(this.getLeftOperand().getType().sameType(this.getRightOperand().getType()));
        Validate.isTrue(this.getLeftOperand().getType().isInt());
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
    }
}
