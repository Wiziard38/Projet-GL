package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

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

}
