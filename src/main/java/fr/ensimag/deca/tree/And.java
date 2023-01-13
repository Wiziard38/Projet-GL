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
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

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
        Label labelOneFalse = new Label("OneFalse" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        Label labelFin = new Label("FinComp" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        int nActualLeft;
        if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()){
            compiler.setD(compiler.getD() + 2);
            compiler.addInstruction(new PUSH(Register.getR(compiler.getN())));
            compiler.setSP(compiler.getSP()+1);
            compiler.setN(compiler.getN()-1);
            nActualLeft = compiler.getN()+1;
            this.getLeftOperand().codeGenInst(compiler);
            compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualLeft)));
            compiler.addInstruction(new BNE(labelOneFalse));
            compiler.addInstruction(new PUSH(Register.getR(compiler.getN())));
            compiler.setSP(compiler.getSP()+1);
            compiler.setN(compiler.getN()-1);
            int nActualRight = compiler.getN()+1;
            this.getRightOperand().codeGenInst(compiler);
            compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualRight)));
            compiler.addInstruction(new BNE(labelOneFalse));
            compiler.addInstruction(new BRA(labelFin));
            compiler.addInstruction(new POP(Register.R0));
            compiler.setSP(compiler.getSP() - 1);
        }
        else{
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler);
            compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualLeft)));
        compiler.addInstruction(new BNE(labelOneFalse));
            if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()){
                compiler.addInstruction(new PUSH(Register.getR(compiler.getN())));
                compiler.setD(compiler.getD() + 1);
                compiler.setSP(compiler.getSP()+1);
                compiler.setN(compiler.getN()-1);
                int nActualRight = compiler.getN()+1;
                this.getRightOperand().codeGenInst(compiler);
                compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualRight)));
                compiler.addInstruction(new BNE(labelOneFalse));
                compiler.addInstruction(new BRA(labelFin));
                compiler.setSP(compiler.getSP() - 1);
                compiler.addInstruction(new POP(Register.getR(0)));
            }
            else {
                int nActualRight = compiler.getN()+1;
                this.getRightOperand().codeGenInst(compiler);
                compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActualRight)));
            compiler.addInstruction(new BNE(labelOneFalse));
            compiler.addInstruction(new BRA(labelFin));
            }
        }
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
