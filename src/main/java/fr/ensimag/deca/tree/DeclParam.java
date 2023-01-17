package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Declaration of a parameter
 */
public class DeclParam extends AbstractDeclParam {

    private AbstractIdentifier type;
    private AbstractIdentifier name;

    public DeclParam(AbstractIdentifier varType, AbstractIdentifier varName) {
        Validate.notNull(varName);
        Validate.notNull(varType);
        type = varType;
        name = varName;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        name.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
    }

    @Override
    public Type verifySigParam(DecacCompiler compiler) throws ContextualError {
        return this.type.verifyType(compiler, true, "un parametre");
    }

    @Override
    public void verifyEnvParam(DecacCompiler compiler, EnvironmentExp localEnv)
            throws ContextualError {

        Type paramType = this.type.verifyType(compiler, true, "un parametre");
        try {
            localEnv.declare(this.name.getName(), new ParamDefinition(paramType, this.getLocation()));
        } catch (DoubleDefException e) {
            throw new ContextualError(String.format("Le nom '%s' apparait dans plusieurs paramètres",
                    this.name), this.getLocation());
        }

        this.name.setDefinition(localEnv.get(this.name.getName()).asParamDefinition(null, null));
    }


}
