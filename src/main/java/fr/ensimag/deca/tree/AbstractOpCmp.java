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
        
        if ((typeLeft.isFloat() || typeLeft.isInt()) 
                && (typeRight.isFloat() || typeRight.isInt())) {

            this.setType(compiler.environmentType.BOOLEAN);
            return compiler.environmentType.BOOLEAN;
        }

        // Code déplacé dans AbstractOpExactCmp.java
        // if (this.getClass() == Equals.class || this.getClass() == NotEquals.class) {        
        //     if ((typeLeft == compiler.environmentType.BOOLEAN) 
        //             && (typeRight == compiler.environmentType.BOOLEAN)) {
                
        //         this.setType(compiler.environmentType.BOOLEAN);
        //         return compiler.environmentType.BOOLEAN;
        //     }
        // }

        throw new ContextualError("Comparaison arithmétique sur des non-nombres", this.getLocation()); // Rule 3.33
    }

}
