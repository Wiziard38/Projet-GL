package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Instruction;


/**
 * Class used to send the DIV instruction depending on wether we compile in ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperDIV {

    public static Instruction main(DVal op1, GPRegister op2, boolean arm) {
        if (arm) {
            op2 = op2.convertToArmRegister();
            return new fr.ensimag.arm.instructions.SDIV(op1, op2);
        } else {
            return new fr.ensimag.ima.instructions.DIV(op1, op2);
        }
    }
}
