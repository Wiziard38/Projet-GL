package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;

/**
 * BGE instruction pour arm
 * 
 * @author gl39
 */
public class BGE extends BranchInstruction {

    public BGE(Label op) {
        super(op);
    }

}
