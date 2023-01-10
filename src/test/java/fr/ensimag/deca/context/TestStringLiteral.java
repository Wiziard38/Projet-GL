package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.StringLiteral;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

/**
 * Test for the StringLiteral node using mockito, using @Mock and @Before annotations.
 *
 * @author Mathis
 * @date 09/01/2023
 */
public class TestStringLiteral {

    DecacCompiler compiler;
    
    @BeforeEach
    public void setup() throws ContextualError {
        //MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);
        compiler = new DecacCompiler(null, null);
    }

    @Test
    public void testBool() throws ContextualError {
        StringLiteral t = new StringLiteral("string");
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isString());
    }
}