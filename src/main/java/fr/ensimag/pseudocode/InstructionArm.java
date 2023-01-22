package fr.ensimag.pseudocode;

import java.io.PrintStream;

public abstract class InstructionArm extends Instruction {

    @Override
    void displayOperands(PrintStream s) {
        // nothing to do, the display is specific because on multiple lines
    }

    abstract public void displayInstructions(PrintStream s);

    @Override
    void display(PrintStream s) {
        this.displayInstructions(s);
    }

}
