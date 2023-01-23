package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Register;


/**
 * STORE instruction pour arm
 * 
 * @author gl39
 */
public class STR extends BinaryInstruction {
    public STR(Register op1, DAddr op2) {
        super(op1, op2);
    }
}