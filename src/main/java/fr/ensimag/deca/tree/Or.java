package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
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
        Label labelOneTrue = new Label("OneTrueOr" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        Label labelFin = new Label("FinCompOr" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        int nActualLeft;
        if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()){
            compiler.setD(compiler.getD() + 2);
            compiler.addInstruction(new PUSH(Register.getR(compiler.getN())));
            compiler.setSP(compiler.getSP()+1);
            compiler.setN(compiler.getN()-1);
            nActualLeft = compiler.getN()+1;
            this.getLeftOperand().codeGenInst(compiler);
            compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualLeft)));
            compiler.addInstruction(new BEQ(labelOneTrue));
            compiler.addInstruction(new PUSH(Register.getR(compiler.getN())));
            compiler.setSP(compiler.getSP()+1);
            compiler.setN(compiler.getN()-1);
            int nActualRight = compiler.getN()+1;
            this.getRightOperand().codeGenInst(compiler);
            compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualRight)));
            compiler.addInstruction(new BEQ(labelOneTrue));
            compiler.addInstruction(new BRA(labelFin));
            compiler.addInstruction(new POP(Register.R0));
            compiler.setSP(compiler.getSP() - 1);
        }
        else{
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler);
            compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualLeft)));
            compiler.addInstruction(new BEQ(labelOneTrue));
            if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()){
                compiler.addInstruction(new PUSH(Register.getR(compiler.getN())));
                compiler.setD(compiler.getD() + 1);
                compiler.setSP(compiler.getSP()+1);
                compiler.setN(compiler.getN()-1);
                int nActualRight = compiler.getN()+1;
                this.getRightOperand().codeGenInst(compiler);
                compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualRight)));
                compiler.addInstruction(new BEQ(labelOneTrue));
                compiler.addInstruction(new BRA(labelFin));
                compiler.setSP(compiler.getSP() - 1);
                compiler.addInstruction(new POP(Register.getR(0)));
            }
            else {
                int nActualRight = compiler.getN()+1;
                this.getRightOperand().codeGenInst(compiler);
                compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualRight)));
                compiler.addInstruction(new BEQ(labelOneTrue));
                compiler.addInstruction(new BRA(labelFin));
            }
        }
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
