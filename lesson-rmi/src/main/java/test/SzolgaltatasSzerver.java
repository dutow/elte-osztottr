package test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class SzolgaltatasSzerver {

    public static void main(String[] argv) {
        try {
            String name = "Szolgaltatas";
            Szolgaltatas engine = new SzolgaltatasImpl();
            Szolgaltatas stub =
                    (Szolgaltatas) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Szolgaltatas bound");
        } catch (Exception e) {
            System.err.println("Szolgaltatas exception:");
            e.printStackTrace();
        }
    }
}
