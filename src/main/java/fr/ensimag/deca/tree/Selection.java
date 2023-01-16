package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Selection of a field
 */
public class Selection extends AbstractCall {

    private AbstractExpr expr;

    public Selection(AbstractExpr e, AbstractIdentifier field_ident) {
        super(field_ident);
        Validate.notNull(field_ident);
        expr = e;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        getFieldIdent().decompile(s);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        ClassType selectClass = super.verifyCallMessage(compiler, localEnv, currentClass,
                "La sélection doit se faire sur une class").asClassType(null, null); // Rule 3.65 // Rule 3.66
        

        FieldDefinition fieldDef = this.verifyFieldIdent(compiler, compiler.environmentType.getClass(
                selectClass.getName()).getMembers());
                
        if (fieldDef.getVisibility() == Visibility.PUBLIC) {
            return fieldDef.getType();
        }
        if (selectClass.isSubClassOf(currentClass.getType())) {
            if (currentClass.getType().isSubClassOf(fieldDef.getContainingClass().getType())) {
                return fieldDef.getType();
            }
        }
        throw new ContextualError(String.format("Le champ '%s' ne peut être accédé localement !",
                this.getFieldIdent()), this.getLocation()); // Rule 3.66
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        getFieldIdent().prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        getFieldIdent().iter(f);
    }

}
