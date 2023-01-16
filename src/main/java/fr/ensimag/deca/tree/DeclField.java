package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclField extends AbstractDeclField {

    private Visibility visibility;
    private AbstractIdentifier type;
    private AbstractIdentifier name;
    private AbstractInitialization initialization;

    public DeclField(Visibility v, AbstractIdentifier type, AbstractIdentifier varName,
            AbstractInitialization initialization) {
        Validate.notNull(v);
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        visibility = v;
        this.type = type;
        this.name = varName;
        this.initialization = initialization;
    }

    @Override
    public void verifyEnvField(DecacCompiler compiler, ClassDefinition currentClassDef,
            AbstractIdentifier superClass) throws ContextualError {

        Type fieldType = this.type.verifyType(compiler, true, "un champ");

        FieldDefinition currentField = new FieldDefinition(fieldType, getLocation(), visibility,
                currentClassDef, currentClassDef.getNumberOfFields() + 1);

        try {
            currentClassDef.getMembers().declare(this.name.getName(), currentField);
        } catch (DoubleDefException e) {
            throw new ContextualError(String.format("Champ '%s' deja declare localement",
                    this.name), this.getLocation()); // Rule 2.4
        }
        currentClassDef.incNumberOfFields();
    }

    @Override
    public void verifyInitField(DecacCompiler compiler, ClassDefinition currentClassDef)
            throws ContextualError {

        FieldDefinition thisDef = currentClassDef.getMembers().get(this.name.getName()).asFieldDefinition(
                "Should not happen, contact developpers please.", this.getLocation());

        this.initialization.verifyInitialization(compiler, thisDef.getType(),
                currentClassDef.getMembers(), currentClassDef);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (visibility == Visibility.PROTECTED) {
            s.print("protected ");
        }
        type.decompile(s);
        s.print(" ");
        name.decompile(s);
        initialization.decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        if (visibility == Visibility.PROTECTED) {
            s.println(prefix + "PROTECTED");
        } else {
            s.println(prefix + "PUBLIC");
        }
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
        initialization.iter(f);
    }

}
