package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Instruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperLEA {

    public static Instruction main(DAddr op1, GPRegister op2, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.LDR(op1, op2);
        } else {
            return new fr.ensimag.ima.instructions.LEA(op1, op2);
        }
    }
}
