package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
/**
 * @author gl39
 * @date 01/01/2023
 */
public class DeclVar extends AbstractDeclVar {
    private static final Logger LOG = Logger.getLogger(DeclVar.class);

    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {

        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    protected void codeGenVar(DecacCompiler compiler){
        compiler.setD(compiler.getD() + 1);
        compiler.addInstruction(new ADDSP(new ImmediateInteger(1)));
        int nAct = compiler.getN()+1;
        initialization.codeGenInst(compiler);
        compiler.setSP(compiler.getSP() + 1);
        VariableDefinition varDef = (VariableDefinition) varName.getDefinition();
        varDef.setOperand(new RegisterOffset(compiler.getSP(), Register.GB));
        compiler.setN(nAct - 1);
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Validate.notNull(localEnv);

        LOG.debug("Verify Decl Var - type");
        // On verifie que le type existe bien
        Type initializationType = this.type.verifyType(compiler, true, "une variable");
        // this.type.setDefinition(compiler.environmentType.defOfType(this.type.getName()));

        LOG.debug("Verify Decl Var - name");
        // On verifie que varName n'est pas deja declare localement
        try {
            this.varName.setDefinition(new VariableDefinition(initializationType, this.getLocation()));
            localEnv.declare(this.varName.getName(), this.varName.getExpDefinition());
        } catch (Exception DoubleDefException) {
            throw new ContextualError(
                    String.format("Le nom de variable '%s' est déjà déclaré dans l'environnement local",
                            this.varName.getName().getName()),
                    this.getLocation()); // Rule 3.17
        }

        LOG.debug("Verify Decl Var - initialization");
        this.initialization.verifyInitialization(compiler, initializationType, localEnv, currentClass);

    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        if (initialization instanceof Initialization) {
            s.print(" = ");
            initialization.decompile(s);
        }
        s.print(";");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}
