package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperBEQ;
import fr.ensimag.superInstructions.SuperCMP;
import fr.ensimag.superInstructions.SuperBRA;
import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperPOP;
import fr.ensimag.superInstructions.SuperPUSH;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    protected void codeGenInst(DecacCompiler compiler, String nameBloc) {
        Label labelOneTrue = new Label(
                "OneTrueOr" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        Label labelFin = new Label("FinCompOr" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        int nActualLeft;
        if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()) {
            BlocInProg.getBlock(nameBloc).incrnbPlacePileNeeded();
            BlocInProg.getBlock(nameBloc).incrnbPlacePileNeeded();
            compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
            compiler.setSP(compiler.getSP() + 1);
            compiler.setN(compiler.getN() - 1);
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler, nameBloc);
            compiler.addInstruction(
                    SuperCMP.main(1, Register.getR(nActualLeft), compiler.compileInArm()));
            compiler.addInstruction(SuperBEQ.main(labelOneTrue, compiler.compileInArm()));
            compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
            compiler.setSP(compiler.getSP() + 1);
            compiler.setN(compiler.getN() - 1);
            int nActualRight = compiler.getN() + 1;
            this.getRightOperand().codeGenInst(compiler, nameBloc);
            compiler.addInstruction(
                    SuperCMP.main(1, Register.getR(nActualRight), compiler.compileInArm()));
            compiler.addInstruction(SuperBEQ.main(labelOneTrue, compiler.compileInArm()));
            compiler.addInstruction(SuperBRA.main(labelFin, compiler.compileInArm()));
            compiler.addInstruction(SuperPOP.main(Register.R0, compiler.compileInArm()));
            compiler.setSP(compiler.getSP() - 1);
        } else {
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler, nameBloc);
            compiler.addInstruction(
                    SuperCMP.main(1, Register.getR(nActualLeft), compiler.compileInArm()));
            compiler.addInstruction(SuperBEQ.main(labelOneTrue, compiler.compileInArm()));
            if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()) {
                compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
                BlocInProg.getBlock(nameBloc).incrnbPlacePileNeeded();
                compiler.setSP(compiler.getSP() + 1);
                compiler.setN(compiler.getN() - 1);
                int nActualRight = compiler.getN() + 1;
                this.getRightOperand().codeGenInst(compiler, nameBloc);
                compiler.addInstruction(
                        SuperCMP.main(1, Register.getR(nActualRight), compiler.compileInArm()));
                compiler.addInstruction(SuperBEQ.main(labelOneTrue, compiler.compileInArm()));
                compiler.addInstruction(SuperBRA.main(labelFin, compiler.compileInArm()));
                compiler.setSP(compiler.getSP() - 1);
                compiler.addInstruction(SuperPOP.main(Register.getR(0), compiler.compileInArm()));
            } else {
                int nActualRight = compiler.getN() + 1;
                this.getRightOperand().codeGenInst(compiler, nameBloc);
                compiler.addInstruction(
                        SuperCMP.main(1, Register.getR(nActualRight), compiler.compileInArm()));
                compiler.addInstruction(SuperBEQ.main(labelOneTrue, compiler.compileInArm()));
                compiler.addInstruction(SuperBRA.main(labelFin, compiler.compileInArm()));
            }
        }
        compiler.addLabel(labelOneTrue);
        compiler.addInstruction(
                SuperLOAD.main(1, Register.getR(nActualLeft), compiler.compileInArm()));
        compiler.addInstruction(SuperBRA.main(labelFin, compiler.compileInArm()));
        compiler.addLabel(labelFin);
        compiler.setN(nActualLeft);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
        // TODO Auto-generated method stub

    }

}
