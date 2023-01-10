package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
/**
 * Binary expressions.
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractBinaryExpr extends AbstractExpr {

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
    protected void codeGenInst(DecacCompiler compiler){
        int nActualLeft = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        int nActualRight = compiler.getN()+1;
        this.getRightOperand().codeGenInst(compiler);
        
        switch(getOperatorName()){
            case "+":
                compiler.addInstruction(new ADD(Register.getR(nActualRight),Register.getR(nActualLeft)));
                break;
            case "-":
                compiler.addInstruction(new SUB(Register.getR(nActualRight),Register.getR(nActualLeft)));
                break;
            case "/":
                if (getLeftOperand().getType().isFloat()){
                    compiler.addInstruction(new DIV(Register.getR(nActualRight),Register.getR(nActualLeft)));
                }
                else{
                    compiler.addInstruction(new QUO(Register.getR(nActualRight),Register.getR(nActualLeft)));
                }
                break;
            case "%":
                compiler.addInstruction(new REM(Register.getR(nActualRight),Register.getR(nActualLeft)));
                break;
            case "*":
                compiler.addInstruction(new MUL(Register.getR(nActualRight),Register.getR(nActualLeft)));
                break;
            case "=":
               VariableDefinition varDef = ((AbstractIdentifier)leftOperand).getVariableDefinition();
               compiler.addInstruction(new STORE(Register.getR(nActualRight), varDef.getOperand()));
               break;
        }
        compiler.setN(nActualLeft);
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler){
        int nActualLeft = compiler.getN()+1;
        this.codeGenInst(compiler);
        if(this.getType().isInt()){
            compiler.addInstruction(new LOAD(new ImmediateInteger(0), Register.getR(1)));
            compiler.addInstruction(new LOAD(Register.getR(nActualLeft),Register.getR(1)));
            compiler.addInstruction(new WINT());
        }
        else{
            compiler.addInstruction(new LOAD(new ImmediateFloat(0), Register.getR(1)));
            compiler.addInstruction(new LOAD(Register.getR(nActualLeft),Register.getR(1)));
            compiler.addInstruction(new WFLOAT());
        }
        compiler.setN(nActualLeft-1);
    }

}
