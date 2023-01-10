package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;
import fr.ensimag.ima.pseudocode.instructions.SNE;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractOpExactCmp extends AbstractOpCmp {

    public AbstractOpExactCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){
        int nActualLeft = compiler.getN()+1;
        this.getLeftOperand().codeGenInst(compiler);
        int nActualRight = compiler.getN()+1;
        this.getRightOperand().codeGenInst(compiler);
        compiler.addInstruction(new CMP(Register.getR(nActualLeft), Register.getR(nActualRight)));
        switch(this.getOperatorName()){
            case "!=":
                compiler.addInstruction(new SNE(Register.getR(nActualLeft)));
                break;
            case "==":
                compiler.addInstruction(new SEQ(Register.getR(nActualLeft)));
                break;
        }
        
        
        compiler.setN(nActualLeft);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        
        Type typeLeft = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type typeRight = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        
    
        if ((typeLeft == compiler.environmentType.BOOLEAN) 
                && (typeRight == compiler.environmentType.BOOLEAN)) {
            
            this.setType(compiler.environmentType.BOOLEAN);
            return compiler.environmentType.BOOLEAN;
        }

        // TODO rajouter pour les classes

        Type returnType;
        try {
            returnType = super.verifyExpr(compiler, localEnv, currentClass);
        } catch (Exception ContextualException) {
            throw new ContextualError("Comparaison d'egalité ni sur des nombres, ni des booleans", 
                    this.getLocation()); // Rule 3.33
        }

        return returnType;
    }

}
