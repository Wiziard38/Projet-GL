package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    protected void codeGenInst(DecacCompiler compiler){
        Label labelOneTrue = new Label("OneTrue");
        Label labelFin = new Label("FinCompOr");

        int nActualLeft = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualLeft)));
        compiler.addInstruction(new BEQ(labelOneTrue));
        int nActualRight = compiler.getN()+1;
        this.getRightOperand().codeGenInst(compiler);
        compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualRight)));
        compiler.addInstruction(new BEQ(labelOneTrue));
        compiler.addInstruction(new BRA(labelFin));
        compiler.addLabel(labelOneTrue);
        compiler.addInstruction(new LOAD(new ImmediateInteger(1), Register.getR(nActualLeft)));
        compiler.addLabel(labelFin);
        compiler.setN(nActualLeft);
    }
    
    @Override
    protected String getOperatorName() {
        return "||";
    }


}
