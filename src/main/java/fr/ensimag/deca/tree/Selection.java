package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

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
public class Selection extends AbstractLValue {
    private static final Logger LOG = Logger.getLogger(Selection.class);

    private AbstractExpr expr;
    private AbstractIdentifier name;

    public AbstractIdentifier getName() {
        return name;
    }

    public Selection(AbstractExpr expr, AbstractIdentifier name) {
        Validate.notNull(name);
        Validate.notNull(expr);
        this.name = name;
        this.expr = expr;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        this.name.decompile(s);
    }

    @Override
    public void verifyLValue(EnvironmentExp localEnv) throws ContextualError {
        // nothing to do
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        ClassType selectClass = this.expr.verifyExpr(compiler, localEnv, currentClass)
                .asClassType("La sélection doit se faire sur une class", 
                this.getLocation()); // Rule 3.65 // Rule 3.66

        FieldDefinition fieldDef = this.name.verifyDefinition(compiler, selectClass.getDefinition().
                getMembers()).asFieldDefinition(String.format("'%s' n'est pas un champ de class",
                this.name.getName()), this.getLocation()); // Rule 3.70
                
        if (fieldDef.getVisibility() == Visibility.PUBLIC) {
            return fieldDef.getType();
        }
        if (selectClass.isSubClassOf(currentClass.getType())) {
            if (currentClass.getType().isSubClassOf(fieldDef.getContainingClass().getType())) {
                this.setType(fieldDef.getType());
                return fieldDef.getType();
            }
        }
        throw new ContextualError(String.format("Le champ '%s' ne peut être accédé localement",
                this.name), this.getLocation()); // Rule 3.66
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.expr.prettyPrint(s, prefix, false);
        this.name.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        this.expr.iter(f);
        this.name.iter(f);
    }

}
