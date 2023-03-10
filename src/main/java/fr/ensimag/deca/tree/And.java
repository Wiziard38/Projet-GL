package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperCMP;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.superInstructions.SuperBNE;
import fr.ensimag.superInstructions.SuperBRA;

import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperPOP;
import fr.ensimag.superInstructions.SuperPUSH;

/**
 * Et logique
 *
 * @author gl39
 * @date 01/01/2023
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * Genration d'un et, on fait attention au nombre de registre restant et dans le cas ou on en a plus on utilise la pile.
     * On utilise deux étiquettes: si un et faux tout et faux et la fin de la comparaison
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param nameBloc le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler, String nameBloc) {
        Label labelOneFalse = new Label(
                "OneFalseAnd" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        Label labelFin = new Label(
                "FinCompAnd" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
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
            compiler.addInstruction(SuperBNE.main(labelOneFalse, compiler.compileInArm()));
            compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
            compiler.setSP(compiler.getSP() + 1);
            compiler.setN(compiler.getN() - 1);
            int nActualRight = compiler.getN() + 1;
            this.getRightOperand().codeGenInst(compiler, nameBloc);
            compiler.addInstruction(
                    SuperCMP.main(1, Register.getR(nActualRight), compiler.compileInArm()));
            compiler.addInstruction(SuperBNE.main(labelOneFalse, compiler.compileInArm()));
            compiler.addInstruction(SuperBRA.main(labelFin, compiler.compileInArm()));
            compiler.addInstruction(SuperPOP.main(Register.R0, compiler.compileInArm()));
            compiler.setSP(compiler.getSP() - 1);
        } else {
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler, nameBloc);
            compiler.addInstruction(
                    SuperCMP.main(1, Register.getR(nActualLeft), compiler.compileInArm()));
            compiler.addInstruction(SuperBNE.main(labelOneFalse, compiler.compileInArm()));
            if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()) {
                compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
                BlocInProg.getBlock(nameBloc).incrnbPlacePileNeeded();
                compiler.setSP(compiler.getSP() + 1);
                compiler.setN(compiler.getN() - 1);
                int nActualRight = compiler.getN() + 1;
                this.getRightOperand().codeGenInst(compiler, nameBloc);
                compiler.addInstruction(
                        SuperCMP.main(1, Register.getR(nActualRight), compiler.compileInArm()));
                compiler.addInstruction(SuperBNE.main(labelOneFalse, compiler.compileInArm()));
                compiler.addInstruction(SuperBRA.main(labelFin, compiler.compileInArm()));
                compiler.setSP(compiler.getSP() - 1);
                compiler.addInstruction(SuperPOP.main(Register.getR(0), compiler.compileInArm()));
            } else {
                int nActualRight = compiler.getN() + 1;
                this.getRightOperand().codeGenInst(compiler, nameBloc);
                compiler.addInstruction(
                        SuperCMP.main(1, Register.getR(nActualRight), compiler.compileInArm()));
                compiler.addInstruction(SuperBNE.main(labelOneFalse, compiler.compileInArm()));
                compiler.addInstruction(SuperBRA.main(labelFin, compiler.compileInArm()));
            }
        }
        compiler.addLabel(labelOneFalse);
        compiler.addInstruction(
                SuperLOAD.main(0, Register.getR(nActualLeft), compiler.compileInArm()));
        compiler.addLabel(labelFin);
        compiler.setN(nActualLeft);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
    }

}
