package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Method declaration
 */
public abstract class AbstractMethod extends Tree {

    private AbstractIdentifier name;
    private AbstractIdentifier returnType;

    public AbstractMethod(AbstractIdentifier method, AbstractIdentifier type) {
        Validate.notNull(type);
        Validate.notNull(method);
        name = method;
        returnType = type;
    }

    abstract public void decompileBody(IndentPrintStream s);

    @Override
    public void decompile(IndentPrintStream s) {
        returnType.decompile(s);
        name.decompile(s);
        decompileBody(s);
    }

    abstract protected void prettyPrintMethodBody(PrintStream s, String prefix);

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnType.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        prettyPrintMethodBody(s, prefix);
    }

    abstract protected void iterMethodBody(TreeFunction f);

    @Override
    protected void iterChildren(TreeFunction f) {
        returnType.iter(f);
        name.iter(f);
    }

}
