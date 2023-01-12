package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

public class AbstractMethod extends Tree {

    private AbstractIdentifier name;
    private AbstractIdentifier returnType;

    public AbstractMethod(AbstractIdentifier method, AbstractIdentifier type) {
        Validate.notNull(type);
        Validate.notNull(method);
        name = method;
        returnType = type;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub

    }

}
