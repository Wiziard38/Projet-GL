package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.superInstructions.SuperRTS;
import fr.ensimag.superInstructions.SuperSUBSP;



/**
 * Method body declaration pour deca.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class MethodBody extends AbstractMethodBody {

    private ListDeclVar variables;
    private ListInst instructions;

    public MethodBody(ListDeclVar var, ListInst inst) {
        Validate.notNull(inst);
        Validate.notNull(var);
        variables = var;
        instructions = inst;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("{");
        s.indent();
        variables.decompile(s);
        s.println();
        instructions.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        variables.prettyPrint(s, prefix, false);
        instructions.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        variables.iter(f);
        instructions.iter(f);
    }

    @Override
    public void verifyBody(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClassDef, Type returnType) throws ContextualError {

        this.variables.verifyListDeclVariable(compiler, localEnv, currentClassDef);
        this.instructions.verifyListInst(compiler, localEnv, currentClassDef, returnType);
    }


    /**
     * Genère le code du corp d'une méthode
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param name le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenInstBody(DecacCompiler compiler, String name) {
        this.variables.codeGenListVarMeth(compiler, name);
        this.instructions.codeGenListInst(compiler, name);
        compiler.addInstruction(SuperSUBSP.main(this.variables.size(), compiler.compileInArm()));
        compiler.setSP(compiler.getSP() - this.variables.size());
        compiler.addInstruction(SuperRTS.main(compiler.compileInArm()));
    }

}
