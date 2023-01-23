package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.pseudocode.*;
import fr.ensimag.superInstructions.*;

import org.apache.log4j.Logger;

/**
 * Binary expressions.
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractBinaryExpr extends AbstractExpr {
    private static final Logger LOG = Logger.getLogger(AbstractBinaryExpr.class);

    public AbstractExpr getLeftOperand() {
        return leftOperand;
    }

    public AbstractExpr getRightOperand() {
        return rightOperand;
    }

    protected void setLeftOperand(AbstractExpr leftOperand) {
        Validate.notNull(leftOperand);
        this.leftOperand = leftOperand;
    }

    protected void setRightOperand(AbstractExpr rightOperand) {
        Validate.notNull(rightOperand);
        this.rightOperand = rightOperand;
    }

    private AbstractExpr leftOperand;
    private AbstractExpr rightOperand;

    public AbstractBinaryExpr(AbstractExpr leftOperand,
            AbstractExpr rightOperand) {
        Validate.notNull(leftOperand, "left operand cannot be null");
        Validate.notNull(rightOperand, "right operand cannot be null");
        Validate.isTrue(leftOperand != rightOperand, "Sharing subtrees is forbidden");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        getLeftOperand().decompile(s);
        s.print(" " + getOperatorName() + " ");
        getRightOperand().decompile(s);
        s.print(")");
    }

    abstract protected String getOperatorName();

    @Override
    protected void iterChildren(TreeFunction f) {
        leftOperand.iter(f);
        rightOperand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        leftOperand.prettyPrint(s, prefix, false);
        rightOperand.prettyPrint(s, prefix, true);
    }

    /**
    * Génère le code pour une expression binaire. il est factoriser dans cette classe et 
    * donc la méthode finit sur un switch case sur le nom de l'opérateur pour ajouter l'instruction adéquate.
    * On fait attention dans ce cas aux registres restant, s'il ne reste plus on push le dernier et on continue
    * @param  compiler L'instance du compilateur ou rajouter le code assembleur
    * @param  nameBloc Le nom du bloc actuel dans lequel on genère l'assembleur (bloc étant le main program ou une méthode)
    * @return      void
    */
    @Override
    protected void codeGenInst(DecacCompiler compiler, String nameBloc) {
        int nActualLeft;
        if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()) {
            BlocInProg.getBlock(nameBloc).incrnbPlacePileNeeded();
            compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
            compiler.setSP(compiler.getSP() + 1);
            compiler.setN(compiler.getN() - 1);
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler, nameBloc);
            compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
            compiler.setSP(compiler.getSP() + 1);
            compiler.setN(compiler.getN() - 1);
            int nActualRight = compiler.getN() + 1;
            this.getRightOperand().codeGenInst(compiler, nameBloc);
            BlocInProg.getBlock(nameBloc).incrnbPlacePileNeeded();
            compiler.setSP(compiler.getSP() + 1);
            compiler.addInstruction(SuperPUSH.main(Register.getR(nActualRight), compiler.compileInArm()));
            switch (getOperatorName()) {
                case "+":
                    compiler.addInstruction(
                            SuperADD.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                    break;
                case "-":
                    compiler.addInstruction(
                            SuperSUB.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                    break;
                case "/":
                    if (getLeftOperand().getType().isFloat()) {
                        compiler.addInstruction(
                                SuperDIV.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                    } else {
                        compiler.addInstruction(
                                SuperQUO.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                    }
                    break;
                case "%":
                    compiler.addInstruction(
                            SuperREM.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                    break;
                case "*":
                    compiler.addInstruction(
                            SuperMUL.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                    break;
                case "!=":
                    compiler.addInstruction(
                            SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                    compiler.addInstruction(SuperSNE.main(Register.getR(nActualRight), compiler.compileInArm()));
                    break;
                case "==":
                    compiler.addInstruction(
                            SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                    compiler.addInstruction(SuperSEQ.main(Register.getR(nActualRight), compiler.compileInArm()));
                    break;
                case ">":
                    compiler.addInstruction(
                            SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                    compiler.addInstruction(SuperSGT.main(Register.getR(nActualRight), compiler.compileInArm()));
                    break;
                case ">=":
                    compiler.addInstruction(
                            SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                    compiler.addInstruction(SuperSGE.main(Register.getR(nActualRight), compiler.compileInArm()));
                    break;
                case "<":
                    compiler.addInstruction(
                            SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                    compiler.addInstruction(SuperSLT.main(Register.getR(nActualRight), compiler.compileInArm()));
                    break;
                case "<=":
                    compiler.addInstruction(
                            SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                    compiler.addInstruction(SuperSLE.main(Register.getR(nActualRight), compiler.compileInArm()));
                    break;
            }
            compiler.addInstruction(SuperPOP.main(Register.R0, compiler.compileInArm()));
            compiler.addInstruction(SuperPOP.main(Register.R0, compiler.compileInArm()));
            compiler.setSP(compiler.getSP() - 2);
        } else {
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler, nameBloc);
            if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()) {
                compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
                BlocInProg.getBlock(nameBloc).incrnbPlacePileNeeded();
                compiler.setSP(compiler.getSP() + 1);
                compiler.setN(compiler.getN() - 1);
                int nActualRight = compiler.getN() + 1;
                this.getRightOperand().codeGenInst(compiler, nameBloc);
                BlocInProg.getBlock(nameBloc).incrnbPlacePileNeeded();
                compiler.setSP(compiler.getSP() + 1);
                compiler.addInstruction(SuperPUSH.main(Register.getR(nActualRight), compiler.compileInArm()));
                compiler.addInstruction(
                        SuperLOAD.main(SuperOffset.main(compiler.getSP() - 1, Register.GB, compiler.compileInArm()),
                                Register.getR(nActualRight), compiler.compileInArm()));
                switch (getOperatorName()) {
                    case "+":
                        compiler.addInstruction(
                                SuperADD.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                        break;
                    case "-":
                        compiler.addInstruction(
                                SuperSUB.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                        break;
                    case "/":
                        if (getLeftOperand().getType().isFloat()) {
                            compiler.addInstruction(SuperDIV.main(
                                    SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                        } else {
                            compiler.addInstruction(SuperQUO.main(
                                    SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                    Register.getR(nActualRight), compiler.compileInArm()));
                        }
                        compiler.addInstruction(SuperBOV.main(compiler.getErreurOverflow(), compiler.compileInArm()));
                        break;
                    case "%":
                        compiler.addInstruction(
                                SuperREM.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                        break;
                    case "*":
                        compiler.addInstruction(
                                SuperMUL.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                        break;
                    case "!=":
                        compiler.addInstruction(
                                SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                        compiler.addInstruction(SuperSNE.main(Register.getR(nActualRight), compiler.compileInArm()));
                        break;
                    case "==":
                        compiler.addInstruction(
                                SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                        compiler.addInstruction(SuperSEQ.main(Register.getR(nActualRight), compiler.compileInArm()));
                        break;
                    case ">":
                        compiler.addInstruction(
                                SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                        compiler.addInstruction(SuperSGT.main(Register.getR(nActualRight), compiler.compileInArm()));
                        break;
                    case ">=":
                        compiler.addInstruction(
                                SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                        compiler.addInstruction(SuperSGE.main(Register.getR(nActualRight), compiler.compileInArm()));
                        break;
                    case "<":
                        compiler.addInstruction(
                                SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                        compiler.addInstruction(SuperSLT.main(Register.getR(nActualRight), compiler.compileInArm()));
                        break;
                    case "<=":
                        compiler.addInstruction(
                                SuperCMP.main(SuperOffset.main(compiler.getSP(), Register.GB, compiler.compileInArm()),
                                        Register.getR(nActualRight), compiler.compileInArm()));
                        compiler.addInstruction(SuperSLE.main(Register.getR(nActualRight), compiler.compileInArm()));
                        break;
                }
                compiler.setSP(compiler.getSP() - 2);
                compiler.addInstruction(SuperPOP.main(Register.getR(0), compiler.compileInArm()));
                compiler.addInstruction(SuperPOP.main(Register.getR(0), compiler.compileInArm()));
            } else {
                int nActualRight = compiler.getN() + 1;
                this.getRightOperand().codeGenInst(compiler, nameBloc);

                switch (getOperatorName()) {
                    case "+":
                        compiler.addInstruction(SuperADD.main(Register.getR(nActualRight), Register.getR(nActualLeft),
                                compiler.compileInArm()));
                        break;
                    case "-":
                        compiler.addInstruction(SuperSUB.main(Register.getR(nActualRight), Register.getR(nActualLeft),
                                compiler.compileInArm()));
                        break;
                    case "/":
                        if (getLeftOperand().getType().isFloat()) {
                            compiler.addInstruction(SuperDIV.main(Register.getR(nActualRight),
                                    Register.getR(nActualLeft), compiler.compileInArm()));
                        } else {
                            compiler.addInstruction(SuperQUO.main(Register.getR(nActualRight),
                                    Register.getR(nActualLeft), compiler.compileInArm()));
                        }
                        compiler.addInstruction(SuperBOV.main(compiler.getErreurOverflow(), compiler.compileInArm()));
                        break;
                    case "%":
                        compiler.addInstruction(SuperREM.main(Register.getR(nActualRight), Register.getR(nActualLeft),
                                compiler.compileInArm()));
                        compiler.addInstruction(SuperBOV.main(compiler.getErreurOverflow(), compiler.compileInArm()));
                        break;
                    case "*":
                        compiler.addInstruction(SuperMUL.main(Register.getR(nActualRight), Register.getR(nActualLeft),
                                compiler.compileInArm()));
                        break;
                    case "!=":
                        compiler.addInstruction(SuperCMP.main(Register.getR(nActualRight), Register.getR(nActualLeft),
                                compiler.compileInArm()));
                        compiler.addInstruction(SuperSNE.main(Register.getR(nActualLeft), compiler.compileInArm()));
                        break;
                    case "==":
                        compiler.addInstruction(SuperCMP.main(Register.getR(nActualRight), Register.getR(nActualLeft),
                                compiler.compileInArm()));
                        compiler.addInstruction(SuperSEQ.main(Register.getR(nActualLeft), compiler.compileInArm()));
                        break;
                    case ">":
                        compiler.addInstruction(SuperCMP.main(Register.getR(nActualRight), Register.getR(nActualLeft),
                                compiler.compileInArm()));
                        compiler.addInstruction(SuperSGT.main(Register.getR(nActualLeft), compiler.compileInArm()));
                        break;
                    case ">=":
                        compiler.addInstruction(SuperCMP.main(Register.getR(nActualRight), Register.getR(nActualLeft),
                                compiler.compileInArm()));
                        compiler.addInstruction(SuperSGE.main(Register.getR(nActualLeft), compiler.compileInArm()));
                        break;
                    case "<":
                        compiler.addInstruction(SuperCMP.main(Register.getR(nActualRight), Register.getR(nActualLeft),
                                compiler.compileInArm()));
                        compiler.addInstruction(SuperSLT.main(Register.getR(nActualLeft), compiler.compileInArm()));
                        break;
                    case "<=":
                        compiler.addInstruction(SuperCMP.main(Register.getR(nActualRight), Register.getR(nActualLeft),
                                compiler.compileInArm()));
                        compiler.addInstruction(SuperSLE.main(Register.getR(nActualLeft), compiler.compileInArm()));
                        break;
                }
            }
        }
        if (this.getType().isFloat()) {
            compiler.addInstruction(SuperBOV.main(compiler.getErreurOverflow(), compiler.compileInArm()));
        }
        compiler.setN(nActualLeft);
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex, String nameBloc) {
        int nActualLeft = compiler.getN() + 1;
        this.codeGenInst(compiler, nameBloc);
        if (this.getType().isInt()) {
            compiler.addInstruction(SuperLOAD.main(0, Register.getR(1), compiler.compileInArm()));
            compiler.addInstruction(
                    SuperLOAD.main(Register.getR(nActualLeft), Register.getR(1), compiler.compileInArm()));
            compiler.addInstruction(SuperWINT.main(compiler.compileInArm()));
        } else {
            compiler.addInstruction(SuperLOAD.main(0, Register.getR(1), compiler.compileInArm()));
            compiler.addInstruction(
                    SuperLOAD.main(Register.getR(nActualLeft), Register.getR(1), compiler.compileInArm()));
            compiler.addInstruction(SuperWFLOAT.main(compiler.compileInArm()));
        }
        compiler.setN(nActualLeft - 1);
    }

}
