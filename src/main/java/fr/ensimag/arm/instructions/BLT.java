package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;


/**
 * BLT instruction pour arm
 * 
 * @author gl39
 */
public class BLT extends BranchInstruction {

    public BLT(Label op) {
        super(op);
    }

}
