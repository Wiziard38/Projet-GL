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
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        ClassType selectClass = this.expr.verifyExpr(compiler, localEnv, currentClass)
                .asClassType("La sélection doit se faire sur une classe", 
                this.getLocation()); // Rule 3.65 // Rule 3.66

        FieldDefinition fieldDef = this.verifyFieldIdent(compiler.environmentType.getClass(
                selectClass.getName()).getMembers());
                
        if (fieldDef.getVisibility() == Visibility.PUBLIC) {
            return fieldDef.getType();
        }
        if (selectClass.isSubClassOf(currentClass.getType())) {
            if (currentClass.getType().isSubClassOf(fieldDef.getContainingClass().getType())) {
                this.setType(fieldDef.getType());
                return fieldDef.getType();
            }
        }
        throw new ContextualError(String.format("Le champ '%s' ne peut être accédé localement !",
                this.name), this.getLocation()); // Rule 3.66
    }

    /**
     * TODO
     */
    public FieldDefinition verifyFieldIdent(EnvironmentExp localEnv) throws ContextualError {

        if (localEnv.get(this.name.getName()) == null) {
            throw new ContextualError(String.format("Le champ '%s' n'est pas défini dans l'environnement local",
                    this.name.getName()), this.getLocation()); // Rule 3.70
        }
        LOG.debug(localEnv.get(this.name.getName()));
        
        FieldDefinition currentField = localEnv.get(this.name.getName()).asFieldDefinition(String.format(
                "'%s' n'est pas un champ de class", this.name.getName()), this.getLocation()); // Rule 3.70

        this.name.setDefinition(currentField);
        return currentField;
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
