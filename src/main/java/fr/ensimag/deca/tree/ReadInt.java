package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperBOV;
import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperRINT;

import java.io.PrintStream;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public class ReadInt extends AbstractReadExpr {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(compiler.environmentType.INT);
        return compiler.environmentType.INT;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        compiler.addInstruction(SuperRINT.main(compiler.compileInArm()));
        int nActual = compiler.getN() + 1;
        compiler.addInstruction(SuperLOAD.main(Register.getR(1), Register.getR(nActual), compiler.compileInArm()));
        compiler.addInstruction(SuperBOV.main(compiler.getErreurinOut(), compiler.compileInArm()));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("readInt()");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

}
