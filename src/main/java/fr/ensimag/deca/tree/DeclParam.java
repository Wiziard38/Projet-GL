package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

public class DeclParam extends AbstractDeclParam {

    private AbstractIdentifier type;
    private AbstractIdentifier name;

    public DeclParam(AbstractIdentifier varType, AbstractIdentifier varName) {
        Validate.notNull(varName);
        Validate.notNull(varType);
        type = varType;
        name = varName;
    }

}
