package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.ReadInt;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

/**
 * Test for the ReadInt node using mockito, using @Mock and @Before annotations.
 *
 * @author Mathis
 * @date 09/01/2023
 */
public class TestReadInt {

    DecacCompiler compiler;
    
    @BeforeEach
    public void setup() throws ContextualError {
        //MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);
        compiler = new DecacCompiler(null, null, false);
    }

    @Test
    public void testBool() throws ContextualError {
        ReadInt t = new ReadInt();
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isInt());
    }
}