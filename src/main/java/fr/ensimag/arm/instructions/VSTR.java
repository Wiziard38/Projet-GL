package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Register;


/**
 * STORE instruction pour floatant arm
 * 
 * @author gl39
 */
public class VSTR extends BinaryInstruction {

    public VSTR(Register op1, DAddr op2) {
        super(op1, op2);
    }
}