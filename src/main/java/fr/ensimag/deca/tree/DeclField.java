package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;

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
    public void verifyEnvField(DecacCompiler compiler, AbstractIdentifier currentClass,
            AbstractIdentifier superClass) throws ContextualError {

        Type fieldType = this.type.verifyType(compiler);
        ClassDefinition currentClassDef = (ClassDefinition) (compiler.environmentType.
                defOfType(currentClass.getName()));
        FieldDefinition currentField = new FieldDefinition(fieldType, getLocation(), visibility,
                currentClassDef, currentClassDef.getNumberOfFields());

        try {
            currentClassDef.getMembers().declare(this.name.getName(), currentField);
        } catch (DoubleDefException e) {
            throw new ContextualError(String.format("Champ '%s' deja declare localement",
                    this.name), this.getLocation()); // Rule 2.4
        }
        currentClassDef.incNumberOfFields();
    }
}
