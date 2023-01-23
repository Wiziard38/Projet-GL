package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperLOAD;

import java.io.PrintStream;

/**
 * Absence of initialization (e.g. "int x;" as opposed to "int x =
 * 42;").
 *
 * @author gl39
 * @date 01/01/2023
 */
public class NoInitialization extends AbstractInitialization {

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // TODO ?
        // throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, Definition def, String nameBloc) {
        int nActual = compiler.getN() + 1;
        compiler.setN(nActual);
        BlocInProg.getBlock(nameBloc).incrnbRegisterNeeded(compiler.getN());
        if (def.getNature().equals("field")) {
            if (def.getType().isBoolean() || def.getType().isInt()) {
                compiler.addInstruction(
                        SuperLOAD.main(0, Register.getR(nActual), compiler.compileInArm()));
            } else if (def.getType().isFloat()) {
                compiler.addInstruction(
                        SuperLOAD.main(0, Register.getR(nActual), compiler.compileInArm()));
            }
        }
    }

    /**
     * Node contains no real information, nothing to check.
     */
    @Override
    protected void checkLocation() {
        // nothing
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // nothing
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
