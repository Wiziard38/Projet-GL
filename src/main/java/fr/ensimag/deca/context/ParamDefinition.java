package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

/**
 * Definition of a method parameter.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class ParamDefinition extends ExpDefinition {
    private int index;

    public ParamDefinition(Type type, Location location, int index) {
        super(type, location);
        this.index = index;
    }

    @Override
    public String getNature() {
        return "parameter";
    }

    @Override
    public boolean isExpression() {
        return true;
    }

    @Override
    public boolean isParam() {
        return true;
    }

    public int getIndex() {
        return this.index;
    }

}
