package fr.ensimag.deca.tree;

import org.apache.log4j.helpers.Loader;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SUB;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BRA;

import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){
        Label labelOneFalse = new Label("OneFalse");
        Label labelFin = new Label("FinCompAnd");

        int nActualLeft = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualLeft)));
        compiler.addInstruction(new BNE(labelOneFalse));
        int nActualRight = compiler.getN()+1;
        this.getRightOperand().codeGenInst(compiler);
        compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualRight)));
        compiler.addInstruction(new BNE(labelOneFalse));
        compiler.addInstruction(new BRA(labelFin));
        compiler.addLabel(labelOneFalse);
        compiler.addInstruction(new LOAD(new ImmediateInteger(0), Register.getR(nActualLeft)));
        compiler.addLabel(labelFin);
        compiler.setN(nActualLeft);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }


}
