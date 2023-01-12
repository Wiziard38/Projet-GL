package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Method body when the body is traditional deca
 */
public class MethodBody extends AbstractMethod {

    private ListDeclParam parameters;
    private ListDeclVar variables;
    private ListInst instructions;

    public MethodBody(AbstractIdentifier method, AbstractIdentifier type, ListDeclParam params, ListDeclVar var,
            ListInst inst) {
        super(method, type);
        Validate.notNull(params);
        Validate.notNull(inst);
        Validate.notNull(var);
        variables = var;
        parameters = params;
        instructions = inst;
    }

    @Override
    public void decompileBody(IndentPrintStream s) {
        s.print("(");
        parameters.decompile(s);
        s.print(") {");
        s.indent();
        variables.decompile(s);
        s.println();
        s.println();
        instructions.decompile(s);
        s.println();
        s.println("}");
    }

    @Override
    protected void prettyPrintMethodBody(PrintStream s, String prefix) {
        parameters.prettyPrint(s, prefix, false);
        variables.prettyPrint(s, prefix, false);
        instructions.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterMethodBody(TreeFunction f) {
        parameters.iter(f);
        variables.iter(f);
        instructions.iter(f);
    }

}
