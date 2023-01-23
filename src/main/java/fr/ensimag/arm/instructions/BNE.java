package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;

/**
 * BNE instruction pour arm
 * 
 * @author gl39
 */
public class BNE extends BranchInstruction {

    public BNE(Label op) {
        super(op);
    }

}
