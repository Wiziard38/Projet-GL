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
        s.println("add R13, [R13], #2");
        s.println("mov [R13, #-1], [R15]");
        s.println("mov [R13], [R11]");
        s.println("mov R11, [R13]");
        s.println("mov R15, [" + op + "]");
    }

}

// ici LB = FP??