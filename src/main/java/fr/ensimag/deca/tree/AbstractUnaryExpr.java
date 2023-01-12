package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.ima.pseudocode.instructions.SNE;

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
    protected void codeGenInst(DecacCompiler compiler){
        int nActual = compiler.getN() +1;
        this.getOperand().codeGenInst(compiler);
        switch(this.getOperatorName()){
            case "/* conv float */":
                compiler.addInstruction(new FLOAT(Register.getR(nActual),Register.getR(nActual)));
                break;
            case "!":
                compiler.addInstruction(new CMP(new ImmediateInteger(1),Register.getR(nActual)));
                compiler.addInstruction(new SNE(Register.getR(nActual)));
                break;
            case "-":
                compiler.addInstruction(new OPP(Register.getR(nActual),Register.getR(nActual)));
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
