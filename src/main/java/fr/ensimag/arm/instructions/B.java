package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;

/**
 * B instruction pour arm
 * 
 * @author gl39
 */
public class B extends BranchInstruction {

    public B(Label op) {
        super(op);
    }

}
