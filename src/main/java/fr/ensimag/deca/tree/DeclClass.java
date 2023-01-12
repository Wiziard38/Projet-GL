package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import org.apache.commons.lang.Validate;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl39
 * @date 01/01/2023
 */
public class DeclClass extends AbstractDeclClass {

    private AbstractIdentifier name;
    private AbstractIdentifier superclass;
    private ListDeclField fields;
    private ListDeclMethod methods;

    public DeclClass(AbstractIdentifier nom, AbstractIdentifier mother, ListDeclField params,
            ListDeclMethod functions) {
        Validate.notNull(nom);
        Validate.notNull(mother);
        Validate.notNull(params);
        Validate.notNull(functions);
        name = nom;
        superclass = mother;
        fields = params;
        methods = functions;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        name.decompile(s);
        s.print("extends ");
        superclass.decompile(s);
        s.println(" {");
        s.indent();
        fields.decompile(s);
        s.println();
        methods.decompile(s);
        s.println();
        s.unindent();
        s.println("}");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {

        if (compiler.environmentType.defOfType(this.superclass.getName()) == null) {
            throw new ContextualError(String.format("Super class '%s' doesn't exists", this.superclass), this.getLocation());
        }

        if (compiler.environmentType.defOfClass(this.superclass.getName()) == null) {
            throw new ContextualError(String.format("Super class '%s' is not a class", this.superclass), this.getLocation());
        }

        try {
            compiler.environmentType.addNewClass(compiler, this.name.getName(), 
                    this.getLocation(), compiler.environmentType.defOfClass(this.superclass.getName()));
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError(String.format("Class '%s' already exists", this.name), this.getLocation());
        }
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        name.prettyPrint(s, prefix, false);
        superclass.prettyPrint(s, prefix, false);
        fields.prettyPrint(s, prefix, false);
        methods.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        name.iter(f);
        superclass.iter(f);
        fields.iter(f);
        methods.iter(f);
    }

}
