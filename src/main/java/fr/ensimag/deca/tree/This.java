package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.superInstructions.SuperLOAD;

/*
 * This keyword
 */
public class This extends AbstractExpr {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        if (currentClass == null) {
            throw new ContextualError("'This' ne peut être appelé en dehors d'une class", this.getLocation()); // Rule 3.43
        }

        this.setType(currentClass.getType());
        return currentClass.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("this");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // nothing to do here
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // nothing to do here
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
        int nActual = compiler.getN() + 1;
        compiler.addInstruction(SuperLOAD.main(new RegisterOffset(-2, Register.LB), Register.getR(nActual), compiler.compileInArm()));
        //compiler.addInstruction(SuperLOAD.main(new RegisterOffset(0, Register.getR(nActual)), Register.getR(nActual), compiler.compileInArm()));

    }
}
