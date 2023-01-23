package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;

/**
 * BOV instruction pour arm
 * 
 * @author gl39
 */
public class BVS extends BranchInstruction {

    public BVS(Label op) {
        super(op);
    }

}