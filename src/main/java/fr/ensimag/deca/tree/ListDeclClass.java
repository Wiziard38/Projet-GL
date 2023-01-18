package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Line;
import fr.ensimag.pseudocode.NullOperand;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.ima.instructions.LOAD;
import fr.ensimag.ima.instructions.PUSH;

import org.apache.log4j.Logger;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");
        for (AbstractDeclClass currentClass : this.getList()) {
            currentClass.verifyClass(compiler);
        }
        LOG.debug("verify listClass: end");
    }

    protected void codeGenListClass(DecacCompiler compiler){
        compiler.setSP(compiler.getSP() + 1);
        compiler.addComment("Class object");
        compiler.environmentType.OBJECT.getDefinition().setOperand(new RegisterOffset(compiler.getSP(), Register.GB));
        compiler.addInstruction(new LOAD(new NullOperand(), Register.getR(compiler.getN())));
        compiler.addInstruction(new PUSH(Register.getR(compiler.getN())));
        compiler.add(new Line(""));
        for(AbstractDeclClass a : this.getList()){
            a.codeGenClass(compiler);
        }
    }

    protected void codeGenCorpMethod(DecacCompiler compiler, String name){
        for(AbstractDeclClass a : this.getList()){
            a.codeGenCorpMethod(compiler, name);
        }
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        for (AbstractDeclClass currentClass : this.getList()) {
            currentClass.verifyClassMembers(compiler);
        }
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        for (AbstractDeclClass currentClass : this.getList()) {
            currentClass.verifyClassBody(compiler);
        }
    }


}
