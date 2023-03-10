package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractOpExactCmp extends AbstractOpCmp {

    public AbstractOpExactCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        Type typeLeft = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type typeRight = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if (typeLeft.isBoolean() && typeRight.isBoolean()) {

            this.setType(compiler.environmentType.BOOLEAN);
            return compiler.environmentType.BOOLEAN;
        }

        if (typeLeft.isClassOrNull() && typeLeft.isClassOrNull()) {
            this.setType(compiler.environmentType.BOOLEAN);
            return compiler.environmentType.BOOLEAN;
        }

        Type returnType;
        try {
            returnType = super.verifyExpr(compiler, localEnv, currentClass);
        } catch (Exception ContextualException) {
            throw new ContextualError("Comparaison d'egalité ni sur des nombres, ni des booleans",
                    this.getLocation()); // Rule 3.33
        }

        return returnType;
    }

    @Override
    protected void checkDecoration() {
        Validate.isTrue(this.getType().isBoolean());
        Validate.isTrue(this.getLeftOperand().getType().sameType(this.getRightOperand().getType()) ||
                (this.getLeftOperand().getType().isClassOrNull() && 
                this.getRightOperand().getType().isClassOrNull()));
        Validate.isTrue(this.getLeftOperand().getType().isInt() || this.getLeftOperand().getType().isFloat()
                || this.getLeftOperand().getType().isBoolean() || this.getLeftOperand().getType().isClassOrNull());
    }
}
