package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SGT;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Greater extends AbstractOpIneq {

    public Greater(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    protected void codeGenInst(DecacCompiler compiler){
        int nActualLeft = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        int nActualRight = compiler.getN()+1;
        this.getRightOperand().codeGenInst(compiler);
        compiler.addInstruction(new CMP(Register.getR(nActualLeft), Register.getR(nActualRight)));
        compiler.addInstruction(new SGT(Register.getR(nActualLeft)));
        compiler.setN(nActualLeft);
    }

    @Override
    protected String getOperatorName() {
        return ">";
    }

}
