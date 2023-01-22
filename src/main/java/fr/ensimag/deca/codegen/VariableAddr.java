package fr.ensimag.deca.codegen;

import java.util.HashMap;

import fr.ensimag.pseudocode.DAddr;

public class VariableAddr {
    private static HashMap<VarInClass, DAddr> varAddr = new HashMap<VarInClass, DAddr>();

    public static void creationVar(String nameClass, String varName, DAddr addr) {
        varAddr.put(new VarInClass(nameClass, varName), addr);
    }

    public static DAddr getVarAddr(String nameClass, String varName) {
        return varAddr.get(new VarInClass(nameClass, varName));
    }

    public static class VarInClass{
        private String className;
        private String varName;

        public VarInClass(String className, String varName){
            this.className = className;
            this.varName = varName;
        }
        public String getClassName(){
            return this.className;
        }

        public String getVarName(){
            return this.varName;
        }
        @Override
        public boolean equals(Object o){
            if(!(o instanceof VarInClass)) {
                return false;
            }
            else {
                VarInClass var = (VarInClass)o;
                return (this.className.equals(var.getClassName()) && this.varName.equals(var.getVarName()));
            }
        }
    }
}
