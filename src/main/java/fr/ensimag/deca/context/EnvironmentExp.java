package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Dictionary associating identifier's ExpDefinition to their names.
 * 
 * This is actually a linked list of dictionaries: each EnvironmentExp has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).
 * 
 * The dictionary at the head of this list thus corresponds to the "current" 
 * block (eg class).
 * 
 * Searching a definition (through method get) is done in the "current" 
 * dictionary and in the parentEnvironment if it fails. 
 * 
 * Insertion (through method declare) is always done in the "current" dictionary.
 * 
 * @author gl39
 * @date 01/01/2023
 */
public class EnvironmentExp {
    private Map<Symbol, ExpDefinition> currentEnvironmentExp 
        = new LinkedHashMap<Symbol, ExpDefinition>();
    private EnvironmentExp parentEnvironment;
    
    public EnvironmentExp(EnvironmentExp parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
    }

    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public ExpDefinition get(Symbol key) {
        if (this.currentEnvironmentExp.containsKey(key)) {
            return this.currentEnvironmentExp.get(key);
        } else if (this.parentEnvironment == null) {
            return null;
        } else {
            return this.parentEnvironment.get(key);
        }
    }

    /**
     * 
     */
    public Map<Symbol, ExpDefinition> getLocalEnv() {
        return this.currentEnvironmentExp;
    }

    /**
     * Add the definition def associated to the symbol name in the environment.
     * 
     * Adding a symbol which is already defined in the environment,
     * - throws DoubleDefException if the symbol is in the "current" dictionary 
     * - or, hides the previous declaration otherwise.
     * 
     * @param name
     *            Name of the symbol to define
     * @param def
     *            Definition of the symbol
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(Symbol name, ExpDefinition def) throws DoubleDefException {
        if (!this.currentEnvironmentExp.containsKey(name)) {
            this.currentEnvironmentExp.put(name, def);
        } else {
            throw new DoubleDefException();
        }
    }

}
