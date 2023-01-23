package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.InstructionArm;

/**
 * RINT instruction pour arm, implémenté mais non fonctionnel car récupère une
 * chaine de caractère
 * 
 * @author gl39
 */
public class RINT extends InstructionArm {
    private static int num = 0;
    private int nb;

    public RINT() {
        nb = num;
        num++;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("mov r7, #0");
        s.println("ldr r1, =inp_str" + nb);
        s.println("mov r2, #4");
        s.println("mov r7, #3");
        s.println("swi 0");
        s.println();
        s.println(".data");
        s.println("inp_str" + nb + ": .asciz \"%  d\"");
        s.println();
        s.println(".text");
    }

}
