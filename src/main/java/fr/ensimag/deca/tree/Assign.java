package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperSTORE;

import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.VariableDefinition;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Assign extends AbstractBinaryExpr {
    private static Logger LOG = Logger.getLogger(Assign.class);

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue) super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
        int nActualRight = compiler.getN() + 1;
        getRightOperand().codeGenInst(compiler, name);
        BlocInProg.getBlock(name).incrnbRegisterNeeded(nActualRight);
        ExpDefinition varDef = ((AbstractIdentifier) getLeftOperand()).getExpDefinition();
        if (varDef.isField()) {
            int nActualAddr = compiler.getN() + 1;
            BlocInProg.getBlock(name).incrnbRegisterNeeded(nActualAddr);
            FieldDefinition fieldDef = (FieldDefinition) varDef;
            compiler.addInstruction(SuperLOAD.main(new RegisterOffset(-2, Register.LB), Register.getR(nActualAddr), compiler.compileInArm()));
            compiler.addInstruction(SuperSTORE.main(Register.getR(nActualRight), new RegisterOffset(fieldDef.getIndex(), Register.getR(nActualAddr)), compiler.compileInArm()));
        }
        else {
            compiler.addInstruction(
                SuperSTORE.main(Register.getR(nActualRight), varDef.getOperand(), compiler.compileInArm()));
        compiler.setN(nActualRight - 1);
        }
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        this.getLeftOperand().verifyLValue(localEnv);
        Type requestedType = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);

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
