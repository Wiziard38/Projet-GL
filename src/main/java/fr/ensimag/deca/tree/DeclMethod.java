package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclMethod extends AbstractDeclMethod {

    private AbstractIdentifier name;
    private AbstractIdentifier returnType;
    private ListDeclParam parameters;
    private AbstractMethodBody body;

    public DeclMethod(AbstractIdentifier method, AbstractIdentifier type, ListDeclParam params,
            AbstractMethodBody body) {

        Validate.notNull(type);
        Validate.notNull(method);
        Validate.notNull(params);
        Validate.notNull(body);
        name = method;
        returnType = type;
        parameters = params;
        this.body = body;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        returnType.decompile(s);
        name.decompile(s);
        body.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnType.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnType.iter(f);
        name.iter(f);
    }

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
