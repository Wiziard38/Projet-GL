package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.superInstructions.SuperRTS;

import org.apache.log4j.Logger;

public class DeclMethod extends AbstractDeclMethod {
    private static final Logger LOG = Logger.getLogger(Program.class);

    public AbstractIdentifier getName(){
        return name;
    }

    private AbstractIdentifier returnType;
    private AbstractIdentifier name;
    private ListDeclParam parameters;
    private AbstractMethodBody body;

    public DeclMethod(AbstractIdentifier method, AbstractIdentifier type, ListDeclParam params,
            AbstractMethodBody body) {

        Validate.notNull(type);
        Validate.notNull(method);
        Validate.notNull(params);
        Validate.notNull(body);
        this.returnType = type;
        this.name = method;
        this.parameters = params;
        this.body = body;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        returnType.decompile(s);
        s.print(" ");
        name.decompile(s);
        s.print("(");
        parameters.decompile(s);
        s.print(") ");
        body.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnType.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        parameters.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnType.iter(f);
        name.iter(f);
        parameters.iter(f);
        body.iter(f);
    }

    public void verifyEnvMethod(DecacCompiler compiler, ClassDefinition currentClassDef,
            AbstractIdentifier superClass) throws ContextualError {

        ClassDefinition superClassDef = (ClassDefinition) (compiler.environmentType.defOfType(superClass.getName()));

        int index = currentClassDef.getNumberOfMethods() + 1;

        // On verifie que le type de retour est conforme
        Type returnMethodType = this.returnType.verifyType(compiler, false, "");

        // On verifie que les paramètres sont conformes et on récupère la signature
        Signature sig = this.parameters.verifySignature(compiler);

        // On verifie que le nom est conforme
        if (currentClassDef.getMembers().get(this.name.getName()) != null) {
            if (superClassDef.getMembers().get(this.name.getName()) == null) {
                throw new ContextualError(String.format("Le nom '%s' est deja déclaré localement",
                        this.name), this.getLocation()); // Rule 2.6
            }

            // C'est une redefinition !
            MethodDefinition overridedMethod = superClassDef.getMembers().get(this.name.getName())
                    .asMethodDefinition(String.format("Le nom '%s' est deja déclaré dans l'environnment",
                    this.name), getLocation()); // Rule 2.7

            if (!overridedMethod.getSignature().differentThan(sig)) {
                throw new ContextualError(String.format(
                        "La signature de la méthode '%s' n'est pas conforme pour une redefinition", this.name),
                        this.getLocation()); // Rule 2.7
            }

            if (!returnMethodType.subType(overridedMethod.getType())) {
                throw new ContextualError(String.format(
                        "Le type de retour de la methode '%s' n'est pas conforme pour une redefinition",
                        this.name), this.getLocation()); // Rule 2.7
            }

            index = overridedMethod.getIndex();
        } else {
            currentClassDef.incNumberOfMethods();
        }

        MethodDefinition current = new MethodDefinition(returnMethodType, this.getLocation(), sig, index);
        currentClassDef.incNumberOfMethods();
        current.setLabel(new Label(currentClassDef.getType().getName().getName() + "." + this.name.getName().getName()));
        try {
            currentClassDef.getMembers().declare(this.name.getName(), current);
        } catch (DoubleDefException e) {
            throw new DecacInternalError("Should not happen, contact developpers please.");
        }

        this.name.setDefinition(current);

    }

    @Override
    public void verifyBodyMethod(DecacCompiler compiler, ClassDefinition currentClassDef)
            throws ContextualError {

        EnvironmentExp localEnv = new EnvironmentExp(currentClassDef.getMembers());
        this.parameters.verifyEnvParams(compiler, localEnv);
        Type returnTypeNonVoid = this.returnType.verifyType(compiler, false, "un return de méthode");
        this.body.verifyBody(compiler, localEnv, currentClassDef, returnTypeNonVoid);
    }

    protected void codeGenCorpMethod(DecacCompiler compiler, String name){
        this.body.codeGenInstBody(compiler, name);
    }
}
