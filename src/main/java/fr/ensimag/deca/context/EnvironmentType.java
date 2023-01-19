package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;

import java.util.HashMap;
import java.util.Map;

import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

// TODO: étendre cette classe pour traiter la partie "avec objet" de Déca
/**
 * Environment containing types. Initially contains predefined identifiers, more
 * classes can be added with declareClass().
 *
 * @author gl39
 * @date 01/01/2023
 */
public class EnvironmentType {
    public EnvironmentType(DecacCompiler compiler) {
        
        envTypes = new HashMap<Symbol, TypeDefinition>();
        
        Symbol intSymb = compiler.createSymbol("int");
        INT = new IntType(intSymb);
        envTypes.put(intSymb, new TypeDefinition(INT, Location.BUILTIN));

        Symbol floatSymb = compiler.createSymbol("float");
        FLOAT = new FloatType(floatSymb);
        envTypes.put(floatSymb, new TypeDefinition(FLOAT, Location.BUILTIN));

        Symbol voidSymb = compiler.createSymbol("void");
        VOID = new VoidType(voidSymb);
        envTypes.put(voidSymb, new TypeDefinition(VOID, Location.BUILTIN));

        Symbol booleanSymb = compiler.createSymbol("boolean");
        BOOLEAN = new BooleanType(booleanSymb);
        envTypes.put(booleanSymb, new TypeDefinition(BOOLEAN, Location.BUILTIN));

        Symbol stringSymb = compiler.createSymbol("string");
        STRING = new StringType(stringSymb);
        // not added to envTypes, it's not visible for the user.
        
        // Init the Object class
        Symbol objectSymb = compiler.createSymbol("Object");
        OBJECT = new ClassType(objectSymb, Location.BUILTIN, null);
        envTypes.put(objectSymb, OBJECT.getDefinition());

        // Init the equals method
        Symbol equalsMethod = compiler.createSymbol("equals");
        Signature equalsSignature = new Signature();
        equalsSignature.add(OBJECT);
        MethodDefinition equalsDef = new MethodDefinition(BOOLEAN, Location.BUILTIN, equalsSignature, 1);

        // Add the method to Object environment
        try {
            OBJECT.getDefinition().getMembers().declare(equalsMethod, equalsDef);
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new DecacInternalError("Should not happen, contact developpers please.");
        }
        OBJECT.getDefinition().incNumberOfMethods();

        // Add the null type
        Symbol nullSymb = compiler.createSymbol("null");
        NULL = new NullType(nullSymb);
        envTypes.put(nullSymb, new TypeDefinition(NULL, Location.BUILTIN));
    }

    private final Map<Symbol, TypeDefinition> envTypes;

    /**
     * TODO
     * @param s
     * @return
     */
    public TypeDefinition defOfType(Symbol s) {
        return envTypes.get(s);
    }


    public final VoidType    VOID;
    public final IntType     INT;
    public final FloatType   FLOAT;
    public final StringType  STRING;
    public final BooleanType BOOLEAN;
    public final ClassType   OBJECT;
    public final NullType    NULL;

    /**
     * TODO
     * @param compiler
     * @param className
     * @param classLocation
     * @param superClass
     * @throws DoubleDefException
     */
    public void addNewClass(DecacCompiler compiler, Symbol className, Location classLocation, 
            ClassDefinition superClass) throws DoubleDefException {
        
        if (envTypes.containsKey(className)) {
            throw new DoubleDefException();
        }

        ClassType classType = new ClassType(className, classLocation, superClass);
        
        envTypes.put(className, classType.getDefinition());
    }

    /**
     * TODO
     * @param className
     * @return
     */
    public ClassDefinition getClass(Symbol className) {
        TypeDefinition def = this.defOfType(className);
        if (def == null || !def.isClass()) {
            throw new DecacInternalError("Should not happen, contact developpers please.");
        }
        return (ClassDefinition) def;
    }

}
