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
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        if ((this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass) != compiler.environmentType.INT) 
                || (this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass) != compiler.environmentType.INT)) {
            return compiler.environmentType.INT;
        }

        throw new ContextualError("Trying modulo with non-INT types", this.getLocation());
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

}
