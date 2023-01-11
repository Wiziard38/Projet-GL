package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

public class MethodBody extends AbstractMethod {

    private ListDeclParam parameters = new ListDeclParam();
    private ListDeclVar variables = new ListDeclVar();
    private ListInst instructions = new ListInst();

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

}
