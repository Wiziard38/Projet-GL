package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;

import java.util.HashMap;
import java.util.Map;
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
        envClass = new HashMap<Symbol, ClassDefinition>();
        
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
        
        // Init object class
        Symbol objectSymb = compiler.createSymbol("Object");
        OBJECT = new ClassType(objectSymb, Location.BUILTIN, null);
        envClass.put(objectSymb, OBJECT.getDefinition());

        // Init equals method
        Symbol equalsMethod = compiler.createSymbol("equals");
        Signature equalsSignature = new Signature();
        equalsSignature.add(OBJECT);
        ExpDefinition equalsDef = new MethodDefinition(OBJECT, Location.BUILTIN, equalsSignature, 0);
        try {
            OBJECT.getDefinition().getMembers().declare(equalsMethod, equalsDef);
            OBJECT.getDefinition().incNumberOfMethods();
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new InternalError("Big proble, equals method should not return DoubleDefException!");
        }
    }

    private final Map<Symbol, TypeDefinition> envTypes;
    private final Map<Symbol, ClassDefinition> envClass;

    /**
     * TODO
     * @param s
     * @return
     */
    public TypeDefinition defOfType(Symbol s) {
        return envTypes.get(s);
    }

    /**
     * TODO
     * @param s
     * @return
     */
    public ClassDefinition defOfClass(Symbol s) {
        return envClass.get(s);
    }

    public final VoidType    VOID;
    public final IntType     INT;
    public final FloatType   FLOAT;
    public final StringType  STRING;
    public final BooleanType BOOLEAN;
    public final ClassType   OBJECT;

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

        ClassType classTyoe = new ClassType(className, classLocation, superClass);
        
        envClass.put(className, classTyoe.getDefinition());
    }

}
