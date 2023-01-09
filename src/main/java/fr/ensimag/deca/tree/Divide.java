package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.QUO;;
/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){
        int nActual = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        this.getRightOperand().codeGenInst(compiler);
        if (getLeftOperand().getType().isFloat()){
            compiler.addInstruction(new DIV(Register.getR(compiler.getN()),Register.getR(nActual)));
        }
        else{
            compiler.addInstruction(new QUO(Register.getR(compiler.getN()),Register.getR(nActual)));
        }
        compiler.setN(nActual);
    }

    @Override
    protected String getOperatorName() {
        return "/";
    }

}
