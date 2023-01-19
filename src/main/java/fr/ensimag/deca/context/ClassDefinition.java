package fr.ensimag.deca.context;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Label;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Definition of a class.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class ClassDefinition extends TypeDefinition {
    private static final Logger LOG = Logger.getLogger(ClassDefinition.class);
    private String name;
    public void setName(String name){
        this.name = name;
    }
    public String getNmae(){
        return this.name;
    }
    private DAddr operand;

    public DAddr getOperand(){
        return operand;
    }

    public void setOperand(DAddr operand){
        this.operand = operand;
    }

    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public void incNumberOfFields() {
        this.numberOfFields++;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods(int n) {
        Validate.isTrue(n >= 0);
        numberOfMethods = n;
    }
    
    public int incNumberOfMethods() {
        numberOfMethods++;
        return numberOfMethods;
    }

    private int numberOfFields = 0;
    private int numberOfMethods = 0;
    
    @Override
    public boolean isClass() {
        return true;
    }
    
    @Override
    public ClassType getType() {
        // Cast succeeds by construction because the type has been correctly set
        // in the constructor.
        return (ClassType) super.getType();
    };

    public ClassDefinition getSuperClass() {
        return superClass;
    }

    private final EnvironmentExp members;
    private final ClassDefinition superClass; 

    public MethodDefinition getMethod(int index) {
        LOG.debug(this.name);
        LOG.debug("index" + index);
        LOG.debug("numberOfMethod" + numberOfMethods);
        assert(index <= numberOfMethods);
        assert(index >= 1);
        Iterator<ExpDefinition> exps = members.getLocalEnv().values().iterator();
        while (exps.hasNext()) {
            ExpDefinition exp = exps.next();
            LOG.debug(exp.isMethod());
            if (exp.isMethod()) {
                LOG.debug(((MethodDefinition)exp).getIndex());
                if (((MethodDefinition)exp).getIndex() == index) {
                    return (MethodDefinition)exp;
                }
            }
        }
        return superClass.getMethod(index);
    }

    public EnvironmentExp getMembers() {
        return members;
    }

    // public EnvironmentExp getMembersFamily() {
        
    // }

    public ClassDefinition(ClassType type, Location location, ClassDefinition superClass) {
        super(type, location);
        EnvironmentExp parent = null;
        if (superClass != null) {
            parent = superClass.getMembers();
        }
        members = new EnvironmentExp(parent);
        this.superClass = superClass;
    }
    
}
