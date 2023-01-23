package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.InstructionArm;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.LabelOperand;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class BSR extends InstructionArm {

    private DVal op;

    public BSR(DVal operand) {
        op = operand;
    }

    public BSR(Label target) {
        this(new LabelOperand(target));
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("add " + fr.ensimag.pseudocode.Register.SP + ", " + fr.ensimag.pseudocode.Register.SP + ", #2");

    }

}

// ici