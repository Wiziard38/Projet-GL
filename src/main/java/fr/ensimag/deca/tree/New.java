package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Call a constructor via keyword 'new'
 */
public class New extends AbstractExpr {

    private AbstractIdentifier name;

    public New(AbstractIdentifier name) {
        this.name = name;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        this.name.decompile(s);
        s.print("()");
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        TypeDefinition exprType = compiler.environmentType.defOfType(this.name.getName());
        if (!exprType.isClass()) {
            throw new ContextualError("'New' ne peut etre affecté que pour une class",
                    this.getLocation()); // Rule 3.42
        }
        
        
        this.setType(exprType.getType());
        this.name.setDefinition(compiler.environmentType.getClass(this.name.getName()));

        return exprType.getType();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.name.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        this.name.iter(f);
    }
}
