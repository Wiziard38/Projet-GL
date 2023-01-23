package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.TernaryInstructionDValToReg;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.Register;

/**
 * SUB instruction pour arm
 * 
 * @author gl39
 */
public class SUB extends TernaryInstructionDValToReg {
    public SUB(DVal op1, Register op2) {
        super(op1, op2);
    }
}
