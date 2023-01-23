package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;

/**
 * Moins unaire
 * 
 * @author gl39
 * @date 01/01/2023
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
    }
}
