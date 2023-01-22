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
        s.println("add R13, R13, #8"); // SP <- V[SP] + 2
        s.println("ldr [R13, #-4], [R15]"); // V[SP]-1 <- V[PC]
        s.println("ldr [R13], [R10]"); // V[SP] <- V[LB]
        s.println("ldr R10, [R13]"); // LB <- V[SP]
        s.println("ldr R15, [" + op + "]"); // PC <- V[dval]
    }

}

// ici