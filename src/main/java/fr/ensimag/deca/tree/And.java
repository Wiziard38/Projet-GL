package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperCMP;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.superInstructions.SuperBNE;
import fr.ensimag.superInstructions.SuperBRA;

import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperPOP;
import fr.ensimag.superInstructions.SuperPUSH;

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
    protected void codeGenInst(DecacCompiler compiler, String name) {
        Label labelOneFalse = new Label(
                "OneFalseAnd" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        Label labelFin = new Label(
                "FinCompAnd" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        int nActualLeft;
        if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()) {
            BlocInProg.getBlock(name).incrnbPlacePileNeeded();
            BlocInProg.getBlock(name).incrnbPlacePileNeeded();
            compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
            compiler.setSP(compiler.getSP() + 1);
            compiler.setN(compiler.getN() - 1);
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler, name);
            compiler.addInstruction(
                    SuperCMP.main(new ImmediateInteger(1), Register.getR(nActualLeft), compiler.compileInArm()));
            compiler.addInstruction(SuperBNE.main(labelOneFalse, compiler.compileInArm()));
            compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
            compiler.setSP(compiler.getSP() + 1);
            compiler.setN(compiler.getN() - 1);
            int nActualRight = compiler.getN() + 1;
            this.getRightOperand().codeGenInst(compiler, name);
            compiler.addInstruction(
                    SuperCMP.main(new ImmediateInteger(1), Register.getR(nActualRight), compiler.compileInArm()));
            compiler.addInstruction(SuperBNE.main(labelOneFalse, compiler.compileInArm()));
            compiler.addInstruction(SuperBRA.main(labelFin, compiler.compileInArm()));
            compiler.addInstruction(SuperPOP.main(Register.R0, compiler.compileInArm()));
            compiler.setSP(compiler.getSP() - 1);
        } else {
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler, name);
            compiler.addInstruction(
                    SuperCMP.main(new ImmediateInteger(1), Register.getR(nActualLeft), compiler.compileInArm()));
            compiler.addInstruction(SuperBNE.main(labelOneFalse, compiler.compileInArm()));
            if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()) {
                compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
                BlocInProg.getBlock(name).incrnbPlacePileNeeded();
                compiler.setSP(compiler.getSP() + 1);
                compiler.setN(compiler.getN() - 1);
                int nActualRight = compiler.getN() + 1;
                this.getRightOperand().codeGenInst(compiler, name);
                compiler.addInstruction(
                        SuperCMP.main(new ImmediateInteger(1), Register.getR(nActualRight), compiler.compileInArm()));
                compiler.addInstruction(SuperBNE.main(labelOneFalse, compiler.compileInArm()));
                compiler.addInstruction(SuperBRA.main(labelFin, compiler.compileInArm()));
                compiler.setSP(compiler.getSP() - 1);
                compiler.addInstruction(SuperPOP.main(Register.getR(0), compiler.compileInArm()));
            } else {
                int nActualRight = compiler.getN() + 1;
                this.getRightOperand().codeGenInst(compiler, name);
                compiler.addInstruction(
                        SuperCMP.main(new ImmediateInteger(1), Register.getR(nActualRight), compiler.compileInArm()));
                compiler.addInstruction(SuperBNE.main(labelOneFalse, compiler.compileInArm()));
                compiler.addInstruction(SuperBRA.main(labelFin, compiler.compileInArm()));
            }
        }
        compiler.addLabel(labelOneFalse);
        compiler.addInstruction(
                SuperLOAD.main(new ImmediateInteger(0), Register.getR(nActualLeft), compiler.compileInArm()));
        compiler.addLabel(labelFin);
        compiler.setN(nActualLeft);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }


}
