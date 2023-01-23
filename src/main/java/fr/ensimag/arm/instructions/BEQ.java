package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;

/**
 * BEQ instruction pour arm
 * 
 * @author gl39
 */
public class BEQ extends BranchInstruction {

    public BEQ(Label op) {
        super(op);
    }

}
