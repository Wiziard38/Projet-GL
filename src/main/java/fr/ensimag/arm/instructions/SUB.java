package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.TernaryInstructionDValToReg;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.Register;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SUB extends TernaryInstructionDValToReg {
    public SUB(DVal op1, Register op2) {
        super(op1, op2);
    }
}
