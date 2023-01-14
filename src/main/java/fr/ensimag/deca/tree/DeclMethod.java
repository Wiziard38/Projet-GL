package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclMethod extends AbstractMethod {

    private AbstractIdentifier name;
    private AbstractIdentifier returnType;
    private ListDeclParam parameters;
    private AbstractMethodBody body;

    public DeclMethod(AbstractIdentifier method, AbstractIdentifier type, ListDeclParam params,
            AbstractMethodBody body) {
        Validate.notNull(type);
        Validate.notNull(method);
        Validate.notNull(params);
        Validate.notNull(body);
        name = method;
        returnType = type;
        parameters = params;
        this.body = body;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        returnType.decompile(s);
        s.print(" ");
        name.decompile(s);
        s.print(" ");
        body.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnType.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnType.iter(f);
        name.iter(f);
    }

}
