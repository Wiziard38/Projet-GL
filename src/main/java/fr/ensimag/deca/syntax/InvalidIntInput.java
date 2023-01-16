package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Exception raised when a Float value is declared too big or too low
 *
 * @author gl39
 * @date 01/01/2023
 */
public class InvalidIntInput extends DecaRecognitionException {

    private final InvalidIntTypes.invalidValue invalid;

    public InvalidIntInput(DecaParser recognizer, ParserRuleContext ctx, InvalidIntTypes.invalidValue value) {
        super(recognizer, ctx);
        this.invalid = value;
    }

    @Override
    public String getMessage() {
        return "Illegal Int value: too " + this.invalid;
    }
    
    private static final long serialVersionUID = 2L;
}

class InvalidIntTypes {
    public enum invalidValue {
        HIGH,
        LOW
    }
}
