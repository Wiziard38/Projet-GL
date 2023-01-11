package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SGE;
import fr.ensimag.ima.pseudocode.instructions.SGT;
import fr.ensimag.ima.pseudocode.instructions.SLE;
import fr.ensimag.ima.pseudocode.instructions.SLT;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractOpIneq extends AbstractOpCmp {
    @Override
    protected void codeGenInst(DecacCompiler compiler){
        int nActualLeft = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        int nActualRight = compiler.getN()+1;
        this.getRightOperand().codeGenInst(compiler);
        compiler.addInstruction(new CMP(Register.getR(nActualRight), Register.getR(nActualLeft)));
        switch(this.getOperatorName()){
            case ">":
                compiler.addInstruction(new SGT(Register.getR(nActualLeft)));
                break;
            case ">=":
                compiler.addInstruction(new SGE(Register.getR(nActualLeft)));
                break;
            case "<":
                compiler.addInstruction(new SLT(Register.getR(nActualLeft)));
                break;
            case "<=":
                compiler.addInstruction(new SLE(Register.getR(nActualLeft)));
                break;
        }
        
        compiler.setN(nActualLeft);
    }

    public AbstractOpIneq(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void checkDecoration() {
        super.checkDecoration();
        if (!this.getLeftOperand().getType().isFloat() && !this.getLeftOperand().getType().isInt()) {
            throw new DecacInternalError("Not both operand of " + this.toString() + " are of Type int or float");
        }
        if (!this.getType().isBoolean()) {
            throw new DecacInternalError("OpIneq " + this.toString() + " is not of Type boolean");
        }
    }

}
