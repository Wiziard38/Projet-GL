package fr.ensimag.ima.instructions;

import fr.ensimag.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class MUL extends BinaryInstructionDValToReg {
    public MUL(DVal op1, GPRegister op2) {
        super(op1, op2);
    }
}
