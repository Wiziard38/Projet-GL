package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

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
        s.println("{");
        s.indent();
        variables.decompile(s);
        s.println();
        s.println();
        instructions.decompile(s);
        s.println();
        s.println("}");

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

}
