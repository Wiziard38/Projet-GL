package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperCMP;
import fr.ensimag.superInstructions.SuperSGE;
import fr.ensimag.superInstructions.SuperSGT;
import fr.ensimag.superInstructions.SuperSLE;
import fr.ensimag.superInstructions.SuperSLT;

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
        Validate.isTrue(this.getLeftOperand().getType().isInt() || this.getRightOperand().getType().isFloat());
        Validate.isTrue(this.getLeftOperand().getType().sameType(this.getRightOperand().getType()));
        Validate.isTrue(this.getType().isBoolean());
    }

}
