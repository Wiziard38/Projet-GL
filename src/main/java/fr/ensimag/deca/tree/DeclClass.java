package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassDefinition;
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
            throw new ContextualError(String.format("La super classe '%s' n'existe pas",
                    this.superclass), this.getLocation()); // Rule 1.3
        }

        if (!compiler.environmentType.defOfType(this.superclass.getName()).isClass()) {
            throw new ContextualError(String.format("'%s' n'est pas une class",
                    this.superclass), this.getLocation()); // Rule 1.3
        }

        ClassDefinition superDef = (ClassDefinition) (compiler.environmentType.
                defOfType(this.superclass.getName()));

        try {
            compiler.environmentType.addNewClass(compiler, this.name.getName(), 
                    this.getLocation(), superDef);
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError(String.format("Le nom '%s' est deja un nom de class",
                    this.name), this.getLocation()); // Rule 1.3
        }
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        
        ClassDefinition currentClassDef = (ClassDefinition) (compiler.environmentType.
                defOfType(this.name.getName()));

        this.fields.verifyListDeclFieldMembers(compiler, currentClassDef, this.superclass);
        this.methods.verifyListDeclMethodMembers(compiler, currentClassDef, this.superclass);
    }

    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {

        ClassDefinition currentClassDef = compiler.environmentType.getClass(this.name.getName());

        this.fields.verifyListDeclFieldBody(compiler, currentClassDef);
        this.methods.verifyListDeclMethodBody(compiler, currentClassDef);
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
