package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentType;

import org.apache.log4j.Logger;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {
    private static final Logger LOG = Logger.getLogger(AbstractBinaryExpr.class);

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        
        Type typeLeft = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type typeRight = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        
        if (typeLeft == compiler.environmentType.FLOAT) {
            if (typeRight == compiler.environmentType.INT) {
                // Case where Float OP Int
                ConvFloat newTreeNode = new ConvFloat(this.getRightOperand());
                this.setRightOperand(newTreeNode);
                return compiler.environmentType.FLOAT;
            }
            if (typeRight == compiler.environmentType.FLOAT) {
                // Case where Float OP Float
                return compiler.environmentType.FLOAT;
            }
        }
        if (typeLeft == compiler.environmentType.INT) {
            if (typeRight == compiler.environmentType.INT) {
                // Case where Int OP Int
                return compiler.environmentType.INT;
            }
            if (typeRight == compiler.environmentType.FLOAT) {
                // Case where Int OP Float
                ConvFloat newTreeNode = new ConvFloat(this.getLeftOperand());
                this.setLeftOperand(newTreeNode);
                return compiler.environmentType.FLOAT;
            }
        }
        throw new ContextualError("Arithmetic operation with non-numbers", this.getLocation());    
    }
}
