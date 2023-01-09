package fr.ensimag.deca.tree;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.SUB;
import fr.ensimag.ima.pseudocode.Register;

/**
 * @author gl39
 * @date 01/01/2023
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){
        int nActual = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        this.getRightOperand().codeGenInst(compiler);
        compiler.addInstruction(new SUB(Register.getR(compiler.getN()),Register.getR(nActual)));
        compiler.setN(nActual);
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }
    
}
