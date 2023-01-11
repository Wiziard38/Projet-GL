package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.DecacInternalError;
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
    private static final Logger LOG = Logger.getLogger(AbstractOpArith.class);

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        
        Type typeLeft = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type typeRight = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        
        if (typeLeft.isFloat()) {
            if (typeRight.isInt()) {
                // Case where Float OP Int
                this.setType(compiler.environmentType.FLOAT);
                ConvFloat newTreeNode = new ConvFloat(this.getRightOperand());
                this.setRightOperand(newTreeNode);
                newTreeNode.setType(compiler.environmentType.FLOAT);
                return compiler.environmentType.FLOAT;
            }
            if (typeRight.isFloat()) {
                // Case where Float OP Float
                this.setType(compiler.environmentType.FLOAT);
                return compiler.environmentType.FLOAT;
            }
        }
        if (typeLeft.isInt()) {
            if (typeRight.isInt()) {
                // Case where Int OP Int
                this.setType(compiler.environmentType.INT);
                return compiler.environmentType.INT;
            }
            if (typeRight.isFloat()) {
                // Case where Int OP Float
                this.setType(compiler.environmentType.FLOAT);
                ConvFloat newTreeNode = new ConvFloat(this.getLeftOperand());
                this.setLeftOperand(newTreeNode);
                newTreeNode.setType(compiler.environmentType.FLOAT);
                return compiler.environmentType.FLOAT;
            }
        }
        throw new ContextualError("Calcul arithmétique sur des non-nombres", this.getLocation()); // Rule 3.33
    }

    @Override
    protected void checkDecoration() {
        super.checkDecoration();
        if (!this.getLeftOperand().getType().isFloat() && !this.getLeftOperand().getType().isInt()) {
            throw new DecacInternalError("Not both operand of " + this.toString() + " are of Type int or float");
        }
        if (!this.getType().isFloat() && !this.getType().isInt()) {
            throw new DecacInternalError("OpArith " + this.toString() + " is not of Type int or float");
        }
    }
}
