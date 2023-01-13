package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SGE;
import fr.ensimag.ima.pseudocode.instructions.SGT;
import fr.ensimag.ima.pseudocode.instructions.SLE;
import fr.ensimag.ima.pseudocode.instructions.SLT;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractOpIneq extends AbstractOpCmp {

    public AbstractOpIneq(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void checkDecoration() {
        super.checkDecoration();
        if (!this.getLeftOperand().getType().isFloat() && !this.getLeftOperand().getType().isInt()) {
            throw new DecacInternalError("Not both operand of " + this.toString() + " are of Type int or float");
        }
        if (!this.getType().isBoolean()) {
            throw new DecacInternalError("OpIneq " + this.toString() + " is not of Type boolean");
        }
    }

}
