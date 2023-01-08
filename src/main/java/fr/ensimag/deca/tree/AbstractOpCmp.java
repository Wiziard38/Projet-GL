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
        
        if ((typeLeft == compiler.environmentType.FLOAT || typeLeft == compiler.environmentType.INT) 
                && (typeRight == compiler.environmentType.FLOAT || typeRight == compiler.environmentType.INT)) {

            this.setType(compiler.environmentType.BOOLEAN);
            return compiler.environmentType.BOOLEAN;
        }
        if (this.getClass() == Equals.class || this.getClass() == NotEquals.class) {        
            if ((typeLeft == compiler.environmentType.BOOLEAN) 
                    && (typeRight == compiler.environmentType.BOOLEAN)) {
                
                this.setType(compiler.environmentType.BOOLEAN);
                return compiler.environmentType.BOOLEAN;
            }
            // TODO rajouter si c'est des type_class !
        }

        throw new ContextualError("Tying to compare with non-numbers", this.getLocation());
    }

}
