package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.TernaryInstructionDValToReg;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class MUL extends TernaryInstructionDValToReg {
    public MUL(DVal op1, GPRegister op2) {
        super(op1, op2);
    }
}
