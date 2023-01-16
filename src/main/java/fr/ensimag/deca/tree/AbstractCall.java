package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Call of a method, a constructor, or a field via selection
 */
public abstract class AbstractCall extends AbstractExpr {

    private AbstractIdentifier field_ident;

    public AbstractCall(AbstractIdentifier field_ident) {
        Validate.notNull(field_ident);
        this.field_ident = field_ident;
    }

    public AbstractIdentifier getFieldIdent() {
        return field_ident;
    }

    public Type verifyCallMessage(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, String message) throws ContextualError {
        Type exprType = this.verifyExpr(compiler, localEnv, currentClass);
        if (!exprType.isClass()) {
            throw new ContextualError(message, this.getLocation());
        }
        this.setType(exprType);
        return exprType;
    }

    public FieldDefinition verifyFieldIdent(DecacCompiler compiler, EnvironmentExp localEnv)
            throws ContextualError {

        if (!this.field_ident.getDefinition().isField()) {
            throw new ContextualError(String.format("'%s' n'est pas un champ de méthode", 
                    this.field_ident.getName()), this.getLocation()); // Rule 3.70
        }

        if (localEnv.get(this.field_ident.getName()) == null) {
            throw new ContextualError(String.format("Le champ '%s' n'est pas défini dans l'environnement local",
                    this.field_ident.getName()), this.getLocation()); // Rule 3.70
        }

        return this.field_ident.getFieldDefinition();
    }

}
