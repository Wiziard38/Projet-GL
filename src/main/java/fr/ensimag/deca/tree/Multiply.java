package fr.ensimag.deca.tree;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.MUL;;;


/**
 * @author gl39
 * @date 01/01/2023
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){
        int nActual = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        this.getRightOperand().codeGenInst(compiler);
        compiler.addInstruction(new MUL(Register.getR(compiler.getN()),Register.getR(nActual)));
        compiler.setN(nActual);
    }

    @Override
    protected String getOperatorName() {
        return "*";
    }

}
