package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.InstructionVideArm;

/**
 * TSTO instruction pour arm
 * 
 * @author gl39
 */
public class TSTO extends InstructionVideArm {
    private ImmediateInteger val;

    public TSTO(ImmediateInteger i) {
        val = i;
    }

    public TSTO(int i) {
        this(new ImmediateInteger(i));
    }

}
