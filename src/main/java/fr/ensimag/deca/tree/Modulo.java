package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.REM;;
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
    protected void codeGenInst(DecacCompiler compiler){
        int nActual = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        this.getRightOperand().codeGenInst(compiler);
        compiler.addInstruction(new REM(Register.getR(compiler.getN()),Register.getR(nActual)));
        compiler.setN(nActual);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        if ((this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass) == compiler.environmentType.INT) 
                && (this.getRightOperand().verifyExpr(compiler, localEnv, currentClass) == compiler.environmentType.INT)) {
            this.setType(compiler.environmentType.INT);
            return compiler.environmentType.INT;
        }
        throw new ContextualError("Tentative de modulo sur des non-entiers", this.getLocation()); // Rule 3.33
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

}
