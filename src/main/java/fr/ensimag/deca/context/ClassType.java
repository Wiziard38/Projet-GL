package fr.ensimag.deca.context;

import org.apache.log4j.Logger;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

/**
 * Type defined by a class.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class ClassType extends Type {
    private static final Logger LOG = Logger.getLogger(ClassType.class);

    protected ClassDefinition definition;
    
    public ClassDefinition getDefinition() {
        return this.definition;
    }

    @Override
    public boolean isClass() {
        return true;
    }

    @Override
    public boolean isClassOrNull() {
        return true;
    }

    /**
     * Standard creation of a type class.
     */
    public ClassType(Symbol className, Location location, ClassDefinition superClass) {
        super(className);
        this.definition = new ClassDefinition(this, location, superClass);
    }

    /**
     * Creates a type representing a class className.
     * (To be used by subclasses only)
     */
    protected ClassType(Symbol className) {
        super(className);
    }
    

    @Override
    public boolean sameType(Type otherType) {
        return otherType.isClass() && (this.getName().equals(otherType.getName()));
    }

    @Override 
    public boolean equals(Object obj) {
        if (obj instanceof ClassType) {
            ClassType anOtherClass = (ClassType) obj;
            return this.getName().getName().equals(anOtherClass.getName().getName());
        }
        return false;
    }

    @Override
    public boolean subType(Type otherType) {
        LOG.debug(otherType);
        if (this.sameType(otherType)) {
            return true;
        }

        if (this.getName().getName() == "Object") {
            return false;
        }

        ClassType superClass = this.getDefinition().getSuperClass().getType();
        return superClass.subType(otherType);
    }
}
