package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;
import fr.ensimag.ima.pseudocode.instructions.SNE;

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

        // TODO rajouter pour les classes

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
        super.checkDecoration();
        if (!this.getLeftOperand().getType().isFloat() && !this.getLeftOperand().getType().isInt()
                && !this.getLeftOperand().getType().isBoolean()) {
            throw new DecacInternalError("Not both operand of " + this.toString() + " are of Type int or float or boolean");
        }
        if (!this.getType().isBoolean()) {
            throw new DecacInternalError("OpCmp " + this.toString() + " is not of Type boolean");
        }
    }
}
