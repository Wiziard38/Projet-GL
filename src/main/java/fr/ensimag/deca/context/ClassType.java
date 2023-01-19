package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

/**
 * Type defined by a class.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class ClassType extends Type {
    
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
        return otherType.isClass();
    }

    /**
     * Return true if potentialSuperClass is a superclass of this class.
     */
    public boolean isSubClassOf(ClassType potentialSuperClass) {
        if (this.equals(potentialSuperClass)) {
            return true;
        }

        if (this.getName().getName() == "Object") {
            return false;
        }

        ClassType superClass = this.getDefinition().getSuperClass().getType();
        return superClass.isSubClassOf(potentialSuperClass);
    }

    @Override 
    public boolean equals(Object obj) {
        if (obj instanceof ClassType) {
            ClassType anOtherClass = (ClassType) obj;
            return this.getName().getName().equals(anOtherClass.getName().getName());
        }
        return false;
    }

}
