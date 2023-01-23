package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.TernaryInstructionDValToReg;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class ADD extends TernaryInstructionDValToReg {
    public ADD(DVal op1, GPRegister op2) {
        super(op1, op2);
    }
}
