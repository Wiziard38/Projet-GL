package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.VariableDefinition;
/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){
        int nActualRight = compiler.getN() +1;
        getRightOperand().codeGenInst(compiler);
        VariableDefinition varDef = ((AbstractIdentifier)getLeftOperand()).getVariableDefinition();
        compiler.addInstruction(new STORE(Register.getR(nActualRight), varDef.getOperand()));
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        
        Type requestedType = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        
        // On set si jamais il y a un CovnFloat a appliquer
        this.setRightOperand(this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, requestedType));

        this.setType(requestedType);
        return requestedType;
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }

}
