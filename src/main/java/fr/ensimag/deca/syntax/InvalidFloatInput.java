package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Exception raised when a Float value is declared too big or too low
 *
 * @author gl39
 * @date 01/01/2023
 */
public class InvalidFloatInput extends DecaRecognitionException {

    private final InvalidFloatTypes.invalidValue invalid;

    public InvalidFloatInput(DecaParser recognizer, ParserRuleContext ctx, InvalidFloatTypes.invalidValue value) {
        super(recognizer, ctx);
        this.invalid = value;
    }

    @Override
    public String getMessage() {
        return "Illegal Float value: too " + this.invalid;
    }
    
    private static final long serialVersionUID = 1L;
}

class InvalidFloatTypes {
    public enum invalidValue {
        HIGH,
        LOW
    }
}
