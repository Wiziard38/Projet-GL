package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Instruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperCMP {

    public static Instruction main(DVal op1, GPRegister op2, boolean arm) {
        if (arm) {
            op2 = op2.convertToArmRegister();
            return new fr.ensimag.arm.instructions.CMP(op1, op2);
        } else {
            return new fr.ensimag.ima.instructions.CMP(op1, op2);
        }
    }

    public static Instruction main(int val, GPRegister op2, boolean arm) {
        if (arm) {
            op2 = op2.convertToArmRegister();
            return new fr.ensimag.arm.instructions.CMP(val, op2);
        } else {
            return new fr.ensimag.ima.instructions.CMP(val, op2);
        }
    }
}