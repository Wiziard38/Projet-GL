package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;

/**
 * Test de supériorité strict
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Greater extends AbstractOpIneq {

    public Greater(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return ">";
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
    }

}
