package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.deca.codegen.VariableAddr;
import fr.ensimag.deca.codegen.VariableAddr.VarInClass;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperSTORE;

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

        if (currentClassDef.getMembers().get(this.name.getName()) != null && 
                !currentClassDef.getMembers().get(this.name.getName()).isField()) {
            
            throw new ContextualError(String.format("Le champ '%s' est déjà défini dans une class mère en tant que méthode",
                    this.name), this.getLocation()); // Rule 2.5
        } else {
            currentClassDef.incNumberOfFields();
        }

        FieldDefinition currentField = new FieldDefinition(fieldType, getLocation(), visibility,
                currentClassDef, currentClassDef.getNumberOfFields());
        
        try {
            currentClassDef.getMembers().declare(this.name.getName(), currentField);
        } catch (DoubleDefException e) {
            throw new ContextualError(String.format("Le champ '%s' est deja déclaré localement",
                    this.name), this.getLocation()); // Rule 2.4
        }

        this.name.setDefinition(currentField);
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
    String prettyPrintNode() {
        return "[visibility=" + this.visibility + "] DeclField";
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
        initialization.iter(f);
    }

    protected void codeGenDeclFiedl(DecacCompiler compiler, String name){
        FieldDefinition defField = (FieldDefinition)this.name.getDefinition();
        int nActual = compiler.getN() + 1;
        initialization.codeGenInst(compiler, this.name.getDefinition(), name);
        int nThis = compiler.getN() + 1;
        compiler.setN(nThis);
        BlocInProg.getBlock(name).incrnbRegisterNeeded(compiler.getN());
        compiler.addInstruction(SuperLOAD.main(new RegisterOffset(-2, Register.LB), Register.getR(nThis), compiler.compileInArm()));
        compiler.addInstruction(SuperSTORE.main(Register.getR(nActual), new RegisterOffset(defField.getIndex(), Register.getR(nThis)), compiler.compileInArm()));
        compiler.setN(nActual - 1);
    }

    @Override
    public AbstractIdentifier getName() {
        return name;
    }
}
