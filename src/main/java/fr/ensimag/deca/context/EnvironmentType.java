package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;

import java.util.HashMap;
import java.util.Map;

import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.pseudocode.Label;

/**
 * Environment containing types. Initially contains predefined identifiers, more
 * classes can be added with declareClass().
 *
 * @author gl39
 * @date 01/01/2023
 */
public class EnvironmentType {
    public EnvironmentType(DecacCompiler decacCompiler) {

        envTypes = new HashMap<Symbol, TypeDefinition>();

        Symbol intSymb = decacCompiler.createSymbol("int");
        INT = new IntType(intSymb);
        envTypes.put(intSymb, new TypeDefinition(INT, Location.BUILTIN));

        Symbol floatSymb = decacCompiler.createSymbol("float");
        FLOAT = new FloatType(floatSymb);
        envTypes.put(floatSymb, new TypeDefinition(FLOAT, Location.BUILTIN));

        Symbol voidSymb = decacCompiler.createSymbol("void");
        VOID = new VoidType(voidSymb);
        envTypes.put(voidSymb, new TypeDefinition(VOID, Location.BUILTIN));

        Symbol booleanSymb = decacCompiler.createSymbol("boolean");
        BOOLEAN = new BooleanType(booleanSymb);
        envTypes.put(booleanSymb, new TypeDefinition(BOOLEAN, Location.BUILTIN));

        Symbol stringSymb = decacCompiler.createSymbol("string");
        STRING = new StringType(stringSymb);
        // not added to envTypes, it's not visible for the user.

        // Init the Object class
        Symbol objectSymb = decacCompiler.createSymbol("Object");
        OBJECT = new ClassType(objectSymb, Location.BUILTIN, null);
        envTypes.put(objectSymb, OBJECT.getDefinition());

        // Init the equals method
        Symbol equalsMethod = decacCompiler.createSymbol("equals");
        Signature equalsSignature = new Signature();
        equalsSignature.add(OBJECT);
        MethodDefinition equalsDef = new MethodDefinition(BOOLEAN, Location.BUILTIN, equalsSignature, 1);
        equalsDef.setLabel(new Label("object.equals"));
        // Add the method to Object environment
        try {
            OBJECT.getDefinition().getMembers().declare(equalsMethod, equalsDef);
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new DecacInternalError("Should not happen, contact developpers please.");
        }
        OBJECT.getDefinition().incNumberOfMethods();

        // Add the null type
        Symbol nullSymb = decacCompiler.createSymbol("null");
        NULL = new NullType(nullSymb);
        envTypes.put(nullSymb, new TypeDefinition(NULL, Location.BUILTIN));
    }

    private final Map<Symbol, TypeDefinition> envTypes;

    /**
     * Method to get a Type definition
     * 
     * @param s reprensents the symbol
     * @return TypeDefinition if the symbol has a Type attached, null othrewise
     */
    public TypeDefinition defOfType(Symbol s) {
        return envTypes.get(s);
    }

    public final VoidType VOID;
    public final IntType INT;
    public final FloatType FLOAT;
    public final StringType STRING;
    public final BooleanType BOOLEAN;
    public final ClassType OBJECT;
    public final NullType NULL;

    /**
     * Adds a new class to the dictionnary of existing types.
     * 
     * @param className symbol reresenting the name of the class
     * @param classLocation location of said class in the deca file
     * @param superClass superclass, defaulted to Object if not specified in input file
     * @throws DoubleDefException if the givent symbol already refers to a declared type
     */
    public void addNewClass(Symbol className, Location classLocation,
            ClassDefinition superClass) throws DoubleDefException {

        if (envTypes.containsKey(className)) {
            throw new DoubleDefException();
        }

        ClassType classType = new ClassType(className, classLocation, superClass);

        envTypes.put(className, classType.getDefinition());
    }

    /**
     * Function that returns a ClassDefinition, provided that the symbol 
     * is indeed of ClassType.
     * 
     * @param className the symbol from which we need to get the definition
     * @return the ClassDefinition
     */
    public ClassDefinition getClass(Symbol className) {
        TypeDefinition def = this.defOfType(className);
        if (def == null || !def.isClass()) {
            throw new DecacInternalError("Should not happen, contact developpers please.");
        }
        return (ClassDefinition) def;
    }

}
