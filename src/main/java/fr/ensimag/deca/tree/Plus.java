package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

/**
 * @author gl39
 * @date 01/01/2023
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){
        int nActual = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        this.getRightOperand().codeGenInst(compiler);
        compiler.addInstruction(new ADD(Register.getR(compiler.getN()),Register.getR(nActual)));
        compiler.setN(nActual);
    }

    @Override
    protected String getOperatorName() {
        return "+";
    }
}
