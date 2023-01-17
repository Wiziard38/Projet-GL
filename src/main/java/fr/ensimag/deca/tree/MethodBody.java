package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Method body when the body is traditional deca
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
        instructions.prettyPrint(s, prefix, false);

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

    @Override
    protected void codeGenInstBody(DecacCompiler compiler) {
        this.variables.codeGenListVar(compiler);
        this.instructions.codeGenListInst(compiler);
        
    }

}
