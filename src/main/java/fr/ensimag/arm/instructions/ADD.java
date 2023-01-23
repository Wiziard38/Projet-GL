package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.TernaryInstructionDValToReg;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.Register;

/**
 * ADD instruction pour arm
 * 
 * @author gl39
 */
public class ADD extends TernaryInstructionDValToReg {
    public ADD(DVal op1, Register op2) {
        super(op1, op2);
    }
}
