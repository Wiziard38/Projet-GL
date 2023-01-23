package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;

/**
 * BGT instruction pour arm
 * 
 * @author gl39
 */
public class BGT extends BranchInstruction {

    public BGT(Label op) {
        super(op);
    }

}
