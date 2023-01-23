package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;

/**
 * Operator "x >= y"
 * 
 * @author gl39
 * @date 01/01/2023
 */
public class GreaterOrEqual extends AbstractOpIneq {

    public GreaterOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return ">=";
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
    }

}
