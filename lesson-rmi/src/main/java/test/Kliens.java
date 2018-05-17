package test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Kliens {

    public static void main(String[] args) {
        try {
            String name = "Szolgaltatas";
            Registry registry = LocateRegistry.getRegistry();
            Szolgaltatas comp = (Szolgaltatas) registry.lookup(name);
            System.out.println(comp.getNumber());
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }

}
