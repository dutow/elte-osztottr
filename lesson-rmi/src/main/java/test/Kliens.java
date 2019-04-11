package test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Kliens {

    public static void main(String[] args) {
        try {
            int port = args.length >= 1 ? Integer.parseInt(args[0]) : 1009;
            String name = "Szolgaltatas";
            Registry registry = LocateRegistry.getRegistry(port);
            Szolgaltatas comp = (Szolgaltatas) registry.lookup(name);
            System.out.println(comp.getNumber());
            System.out.println(comp.getRemoteObject().foo());
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }

}
