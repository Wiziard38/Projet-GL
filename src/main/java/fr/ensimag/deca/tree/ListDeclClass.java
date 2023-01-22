package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.LabelOperand;
import fr.ensimag.pseudocode.Line;
import fr.ensimag.pseudocode.NullOperand;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.superInstructions.SuperCMP;
import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperOffset;
import fr.ensimag.superInstructions.SuperPOP;
import fr.ensimag.superInstructions.SuperPUSH;
import fr.ensimag.superInstructions.SuperRTS;
import fr.ensimag.superInstructions.SuperSEQ;
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

    protected void codeGenListClass(DecacCompiler compiler) {
        compiler.setSP(compiler.getSP() + 2);
        compiler.addComment("Class object");
        compiler.environmentType.OBJECT.getDefinition()
                .setOperand(SuperOffset.main(1, Register.GB, compiler.compileInArm()));
        compiler.addInstruction(
                SuperLOAD.main(new NullOperand(), Register.getR(compiler.getN()), compiler.compileInArm()));
        compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN()), compiler.compileInArm()));
        compiler.addInstruction(SuperLOAD.main(new LabelOperand(new Label("object.equals")),
                Register.getR(compiler.getN() + 1), compiler.compileInArm()));
        compiler.addInstruction(SuperPUSH.main(Register.getR(compiler.getN() + 1), compiler.compileInArm()));
        compiler.add(new Line(""));
        for (AbstractDeclClass a : this.getList()) {
            a.codeGenClass(compiler);
        }
    }

    protected void codeGenCorpMethod(DecacCompiler compiler, String name) {
        if (!compiler.compileInArm()) {
            compiler.addLabel(new Label("object.equals"));
            compiler.addInstruction(SuperPUSH.main(Register.getR(3),
                    compiler.compileInArm()));
            compiler.addInstruction(SuperPUSH.main(Register.getR(2),
                    compiler.compileInArm()));
            compiler.addInstruction(
                    SuperLOAD.main(SuperOffset.main(-2, Register.LB, compiler.compileInArm()),
                            Register.getR(2),
                            compiler.compileInArm()));
            compiler.addInstruction(
                    SuperLOAD.main(SuperOffset.main(-3, Register.LB, compiler.compileInArm()),
                            Register.getR(3),
                            compiler.compileInArm()));
            compiler.addInstruction(SuperCMP.main(Register.getR(2), Register.getR(3),
                    compiler.compileInArm()));
            compiler.addInstruction(SuperSEQ.main(Register.R0, compiler.compileInArm()));
            compiler.addInstruction(SuperPOP.main(Register.getR(2),
                    compiler.compileInArm()));
            compiler.addInstruction(SuperPOP.main(Register.getR(3),
                    compiler.compileInArm()));
            compiler.addInstruction(SuperRTS.main(compiler.compileInArm()));
            compiler.addComment("");
            for (AbstractDeclClass a : this.getList()) {
                a.codeGenCorpMethod(compiler, name);
            }
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        for (AbstractDeclClass currentClass : this.getList()) {
            currentClass.verifyClass(compiler);
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
