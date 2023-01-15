package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

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

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        int nActualLeft;
        if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()) {
            compiler.setD(compiler.getD() + 2);
            compiler.addInstruction(new PUSH(Register.getR(compiler.getN())));
            compiler.setSP(compiler.getSP() + 1);
            compiler.setN(compiler.getN() - 1);
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler);
            compiler.addInstruction(new PUSH(Register.getR(compiler.getN())));
            compiler.setSP(compiler.getSP() + 1);
            compiler.setN(compiler.getN() - 1);
            int nActualRight = compiler.getN() + 1;
            this.getRightOperand().codeGenInst(compiler);
            compiler.setD(compiler.getD() + 1);
            compiler.setSP(compiler.getSP() + 1);
            compiler.addInstruction(new PUSH(Register.getR(nActualRight)));
            switch (getOperatorName()) {
                case "+":
                    compiler.addInstruction(
                            new ADD(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                    break;
                case "-":
                    compiler.addInstruction(
                            new SUB(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                    break;
                case "/":
                    if (getLeftOperand().getType().isFloat()) {
                        compiler.addInstruction(new DIV(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                    } else {
                        compiler.addInstruction(new QUO(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                    }
                    break;
                case "%":
                    compiler.addInstruction(
                            new REM(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                    break;
                case "*":
                    compiler.addInstruction(
                            new MUL(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                    break;
                case "!=":
                    compiler.addInstruction(
                            new CMP(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                    compiler.addInstruction(new SNE(Register.getR(nActualRight)));
                    break;
                case "==":
                    compiler.addInstruction(
                            new CMP(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                    compiler.addInstruction(new SEQ(Register.getR(nActualRight)));
                    break;
                case ">":
                    compiler.addInstruction(
                            new CMP(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                    compiler.addInstruction(new SGT(Register.getR(nActualRight)));
                    break;
                case ">=":
                    compiler.addInstruction(
                            new CMP(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                    compiler.addInstruction(new SGE(Register.getR(nActualRight)));
                    break;
                case "<":
                    compiler.addInstruction(
                            new CMP(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                    compiler.addInstruction(new SLT(Register.getR(nActualRight)));
                    break;
                case "<=":
                    compiler.addInstruction(
                            new CMP(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                    compiler.addInstruction(new SLE(Register.getR(nActualRight)));
                    break;
            }
            compiler.addInstruction(new POP(Register.R0));
            compiler.addInstruction(new POP(Register.R0));
            compiler.setSP(compiler.getSP() - 2);
        } else {
            nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenInst(compiler);
            if (compiler.getN() >= compiler.getCompilerOptions().getnumberRegisters()) {
                compiler.addInstruction(new PUSH(Register.getR(compiler.getN())));
                compiler.setD(compiler.getD() + 1);
                compiler.setSP(compiler.getSP() + 1);
                compiler.setN(compiler.getN() - 1);
                int nActualRight = compiler.getN() + 1;
                this.getRightOperand().codeGenInst(compiler);
                compiler.setD(compiler.getD() + 1);
                compiler.setSP(compiler.getSP() + 1);
                compiler.addInstruction(new PUSH(Register.getR(nActualRight)));
                compiler.addInstruction(new LOAD(new RegisterOffset(compiler.getSP()-1, Register.GB), Register.getR(nActualRight)));
                switch (getOperatorName()) {
                    case "+":
                        compiler.addInstruction(new ADD(new RegisterOffset(compiler.getSP(), Register.GB), Register.getR(nActualRight)));
                        break;
                    case "-":
                        compiler.addInstruction(new SUB(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                        break;
                    case "/":
                        if (getLeftOperand().getType().isFloat()) {
                            compiler.addInstruction(new DIV(new RegisterOffset(compiler.getSP(), Register.GB),
                                    Register.getR(nActualRight)));
                        } else {
                            compiler.addInstruction(new QUO(new RegisterOffset(compiler.getSP(), Register.GB),
                                    Register.getR(nActualRight)));
                        }
                        break;
                    case "%":
                        compiler.addInstruction(new REM(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                        break;
                    case "*":
                        compiler.addInstruction(new MUL(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                        break;
                    case "!=":
                        compiler.addInstruction(new CMP(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                        compiler.addInstruction(new SNE(Register.getR(nActualRight)));
                        break;
                    case "==":
                        compiler.addInstruction(new CMP(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                        compiler.addInstruction(new SEQ(Register.getR(nActualRight)));
                        break;
                    case ">":
                        compiler.addInstruction(new CMP(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                        compiler.addInstruction(new SGT(Register.getR(nActualRight)));
                        break;
                    case ">=":
                        compiler.addInstruction(new CMP(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                        compiler.addInstruction(new SGE(Register.getR(nActualRight)));
                        break;
                    case "<":
                        compiler.addInstruction(new CMP(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                        compiler.addInstruction(new SLT(Register.getR(nActualRight)));
                        break;
                    case "<=":
                        compiler.addInstruction(new CMP(new RegisterOffset(compiler.getSP(), Register.GB),
                                Register.getR(nActualRight)));
                        compiler.addInstruction(new SLE(Register.getR(nActualRight)));
                        break;
                }
                compiler.setSP(compiler.getSP() - 2);
                compiler.addInstruction(new POP(Register.getR(0)));
                compiler.addInstruction(new POP(Register.getR(0)));
            } else {
                int nActualRight = compiler.getN() + 1;
                this.getRightOperand().codeGenInst(compiler);
                
                switch (getOperatorName()) {
                    case "+":
                        compiler.addInstruction(new ADD(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        break;
                    case "-":
                        compiler.addInstruction(new SUB(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        break;
                    case "/":
                        if (getLeftOperand().getType().isFloat()) {
                            compiler.addInstruction(new DIV(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        } else {
                            compiler.addInstruction(new QUO(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        }
                        break;
                    case "%":
                        compiler.addInstruction(new REM(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        compiler.addInstruction(new BOV(compiler.getErreurOverflow()));
                        break;
                    case "*":
                        compiler.addInstruction(new MUL(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        break;
                    case "!=":
                        compiler.addInstruction(new CMP(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        compiler.addInstruction(new SNE(Register.getR(nActualLeft)));
                        break;
                    case "==":
                        compiler.addInstruction(new CMP(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        compiler.addInstruction(new SEQ(Register.getR(nActualLeft)));
                        break;
                    case ">":
                        compiler.addInstruction(new CMP(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        compiler.addInstruction(new SGT(Register.getR(nActualLeft)));
                        break;
                    case ">=":
                        compiler.addInstruction(new CMP(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        compiler.addInstruction(new SGE(Register.getR(nActualLeft)));
                        break;
                    case "<":
                        compiler.addInstruction(new CMP(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        compiler.addInstruction(new SLT(Register.getR(nActualLeft)));
                        break;
                    case "<=":
                        compiler.addInstruction(new CMP(Register.getR(nActualRight), Register.getR(nActualLeft)));
                        compiler.addInstruction(new SLE(Register.getR(nActualLeft)));
                        break;
                }
            }
        }
        if (this.getType().isFloat()) {
            compiler.addInstruction(new BOV(compiler.getErreurOverflow()));
        }
        compiler.setN(nActualLeft);
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        int nActualLeft = compiler.getN() + 1;
        this.codeGenInst(compiler);
        if (this.getType().isInt()) {
            compiler.addInstruction(new LOAD(new ImmediateInteger(0), Register.getR(1)));
            compiler.addInstruction(new LOAD(Register.getR(nActualLeft), Register.getR(1)));
            compiler.addInstruction(new WINT());
        } else {
            compiler.addInstruction(new LOAD(new ImmediateFloat(0), Register.getR(1)));
            compiler.addInstruction(new LOAD(Register.getR(nActualLeft), Register.getR(1)));
            compiler.addInstruction(new WFLOAT());
        }
        compiler.setN(nActualLeft - 1);
    }

}
