package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * List of parameters' declaration
 */
public class ListDeclParam extends TreeList<AbstractDeclParam> {

    @Override
    public void decompile(IndentPrintStream s) {
        int count = getList().size();
        for (AbstractDeclParam p : getList()) {
            p.decompile(s);
            if (count != 1) {
                s.print(", ");
            }
            count--;
        }
    }

    /**
     * TODO
     * @param compiler
     * @return
     * @throws ContextualError
     */
    public Signature verifySignature(DecacCompiler compiler) throws ContextualError {
        Signature sig = new Signature();
        for (AbstractDeclParam signatureParam : this.getList()) {
            Type paramType = signatureParam.verifySigParam(compiler);
            sig.add(paramType);
        }
        return sig;
    }


    public void verifyEnvParams(DecacCompiler compiler, EnvironmentExp localEnv)
            throws ContextualError {
        int paramIndex = 1;
        for (AbstractDeclParam signatureParam : this.getList()) {
            signatureParam.verifyEnvParam(compiler, localEnv, paramIndex);
            paramIndex++;
        }
    }

}
