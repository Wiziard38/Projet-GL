package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Operand;

/**
 * Definition of a Deca type (builtin or class).
 *
 * @author gl39
 * @date 01/01/2023
 */
public class TypeDefinition extends Definition {

    public TypeDefinition(Type type, Location location) {
        super(type, location);
    }

    @Override
    public String getNature() {
        return "type";
    }

    @Override
    public boolean isExpression() {
        return false;
    }
    
}
