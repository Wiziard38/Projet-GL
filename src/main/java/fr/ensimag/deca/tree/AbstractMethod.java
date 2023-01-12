package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;

public abstract class AbstractMethod extends Tree {

    private AbstractIdentifier name;
    private AbstractIdentifier returnType;

    public AbstractMethod(AbstractIdentifier method, AbstractIdentifier type) {
        Validate.notNull(type);
        Validate.notNull(method);
        name = method;
        returnType = type;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub

    }

    /**
     * TODO
     * @param compiler
     * @param currentClass
     * @param superClass
     * @throws ContextualError
     */
    public void verifyEnvMethod(DecacCompiler compiler, AbstractIdentifier currentClass, 
            AbstractIdentifier superClass) throws ContextualError {
        // Verify return Type
        Type returnMethodType = this.returnType.verifyType(compiler);
        ClassDefinition currentClassDef = (ClassDefinition) (compiler.environmentType.
                defOfType(currentClass.getName()));

        // Get signature and verify Types
        //for ()
        
        /*
        FieldDefinition currentField = new FieldDefinition(fieldType, getLocation(), visibility,
                currentClassDef, currentClassDef.getNumberOfFields());

        try {
            currentClassDef.getMembers().declare(this.name.getName(), currentField);
        } catch (DoubleDefException e) {
            throw new ContextualError(String.format("Champ '%s' deja declare localement",
                    this.name), this.getLocation()); // Rule 2.4
        }
        currentClassDef.incNumberOfFields(); */
    }

}
