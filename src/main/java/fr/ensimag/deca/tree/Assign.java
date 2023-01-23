package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperOffset;
import fr.ensimag.superInstructions.SuperSTORE;

import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.ParamDefinition;
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

    /**
     * Genère le code d'une assignation.
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param nameBloc le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler, String nameBloc) {

        int nActualRight = compiler.getN() + 1;
        getRightOperand().codeGenInst(compiler, nameBloc);
        BlocInProg.getBlock(nameBloc).incrnbRegisterNeeded(nActualRight);

        ExpDefinition varDef = getLeftOperand().getExpDefinition();
        if (varDef.isField()) {
            int nActualLeft = compiler.getN() + 1;
            this.getLeftOperand().codeGenVarAddr(compiler, nameBloc);
            BlocInProg.getBlock(nameBloc).incrnbRegisterNeeded(nActualLeft);
            FieldDefinition fieldDef = (FieldDefinition) varDef;
            LOG.debug(fieldDef);
            compiler.addInstruction(SuperSTORE.main(Register.getR(nActualRight),
                    new RegisterOffset(0, Register.getR(nActualLeft)), compiler.compileInArm()));
        } else if (varDef.isParam()) {
            ParamDefinition defParam = (ParamDefinition) varDef;
            compiler.addInstruction(SuperSTORE.main(Register.getR(nActualRight),
                    SuperOffset.main(-defParam.getIndex() - 2, Register.LB, compiler.compileInArm()),
                    compiler.compileInArm()));
        } else {
            compiler.addInstruction(
                    SuperSTORE.main(Register.getR(nActualRight), varDef.getOperand(), compiler.compileInArm()));
            compiler.setN(nActualRight - 1);
        }
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        Type requestedType = this.getLeftOperand().verifyLValue(compiler, localEnv, currentClass);

        // On set si jamais il y a un CovnFloat a appliquer
        this.setRightOperand(this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, requestedType));

        this.setType(requestedType);
        return requestedType;
    }

    @Override
    protected String getOperatorName() {
        return "=";
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
        // TODO Auto-generated method stub

    }

}
