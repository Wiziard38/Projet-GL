package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractOpBool extends AbstractBinaryExpr {

    public AbstractOpBool(AbstractExpr leftOperand, AbstractExpr rightOperand) {
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

        throw new ContextualError("Comparaison logique sur des non-booleans", this.getLocation()); // Rule 3.33
    
    }

    @Override
    protected void checkDecoration() {
        Validate.isTrue(this.getType().sameType(this.getRightOperand().getType()));
        Validate.isTrue(this.getLeftOperand().getType().sameType(this.getRightOperand().getType()));
        Validate.isTrue(this.getLeftOperand().getType().isBoolean());
    }

}
