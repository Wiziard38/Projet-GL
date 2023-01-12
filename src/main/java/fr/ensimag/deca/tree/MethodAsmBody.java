package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Method body when the body is assembler lines of code
 */
public class MethodAsmBody extends AbstractMethod {

    private String textAsm;
    private Location location;

    public MethodAsmBody(AbstractIdentifier method, AbstractIdentifier type, String txt, Location loc) {
        super(method, type);
        Validate.notNull(loc);
        Validate.notNull(txt);
        textAsm = txt;
        location = loc;
    }

    @Override
    public void decompileBody(IndentPrintStream s) {
        s.println(" asm(");
        s.indent();
        s.println(textAsm);
        s.print(");");
    }

    @Override
    protected void prettyPrintMethodBody(PrintStream s, String prefix) {
        s.println(prefix + "+> " + location + " Assembler (" + textAsm + ")");
    }

    @Override
    protected void iterMethodBody(TreeFunction f) {
        // nothing to do here
    }

}
