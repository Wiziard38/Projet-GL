package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperCMP;
import fr.ensimag.superInstructions.SuperFLOAT;
import fr.ensimag.superInstructions.SuperOPP;
import fr.ensimag.superInstructions.SuperSNE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

import org.apache.log4j.Logger;

/**
 * Unary expression.
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractUnaryExpr extends AbstractExpr {
    private static final Logger LOG = Logger.getLogger(AbstractBinaryExpr.class);

    public AbstractExpr getOperand() {
        return operand;
    }

    private AbstractExpr operand;

    public AbstractUnaryExpr(AbstractExpr operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
        int nActual = compiler.getN() + 1;
        this.getOperand().codeGenInst(compiler, name);
        switch (this.getOperatorName()) {
            case "/* conv float */":
                compiler.addInstruction(
                        SuperFLOAT.main(Register.getR(nActual), Register.getR(nActual), compiler.compileInArm()));
                break;
            case "!":
                compiler.addInstruction(
                        SuperCMP.main(new ImmediateInteger(1), Register.getR(nActual), compiler.compileInArm()));
                compiler.addInstruction(SuperSNE.main(Register.getR(nActual), compiler.compileInArm()));
                break;
            case "-":
                compiler.addInstruction(
                        SuperOPP.main(Register.getR(nActual), Register.getR(nActual), compiler.compileInArm()));
                break;
        }
        compiler.setN(nActual);
    }

    protected abstract String getOperatorName();

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(getOperatorName());
        getOperand().decompile(s);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        operand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        operand.prettyPrint(s, prefix, true);
    }

}
