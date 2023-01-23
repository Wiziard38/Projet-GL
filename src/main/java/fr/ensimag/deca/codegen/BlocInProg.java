package fr.ensimag.deca.codegen;

import java.util.HashMap;

public class BlocInProg {
    
    private static HashMap<String, Bloc> listBloc = new HashMap<String, Bloc>();

    public static void addBloc(String name, int line, int register, int place){
        Bloc newBloc = new Bloc(line, register, place);
        listBloc.put(name, newBloc);
    }

    public static Bloc getBlock(String name){
        return listBloc.get(name);
    }
}
