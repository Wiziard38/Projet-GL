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
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
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
                this.setType(compiler.environmentType.BOOLEAN);
                ConvFloat newTreeNode = new ConvFloat(this.getRightOperand());
                this.setRightOperand(newTreeNode);
                newTreeNode.verifyExpr(compiler, localEnv, currentClass);
                return compiler.environmentType.BOOLEAN;
            }
            if (typeRight.isFloat()) {
                // Case where Float OP Float
                this.setType(compiler.environmentType.BOOLEAN);
                return compiler.environmentType.BOOLEAN;
            }
        }
        if (typeLeft.isInt()) {
            if (typeRight.isInt()) {
                // Case where Int OP Int
                this.setType(compiler.environmentType.BOOLEAN);
                return compiler.environmentType.BOOLEAN;
            }
            if (typeRight.isFloat()) {
                // Case where Int OP Float
                this.setType(compiler.environmentType.BOOLEAN);
                ConvFloat newTreeNode = new ConvFloat(this.getLeftOperand());
                this.setLeftOperand(newTreeNode);
                newTreeNode.verifyExpr(compiler, localEnv, currentClass);
                return compiler.environmentType.BOOLEAN;
            }
        }

        throw new ContextualError("Comparaison arithmétique sur des non-nombres", this.getLocation()); // Rule 3.33
    }

}
