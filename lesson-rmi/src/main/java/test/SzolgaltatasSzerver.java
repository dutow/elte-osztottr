package test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class SzolgaltatasSzerver {

    public static void main(String[] argv) {

        // Parameterezes:
        // semmi : registry-t keres az alapertelezett 1009-es porton
        // --with-registry : registry-t indit az alapertelmezett porton
        // --with-registry <port> : registry-t indit a megadott porton
        // <port> : registry-t keres a megadott porton


        // Ha nincs parancssori parameter, az RMI registry-t manualisan kell inditani
        // Windows: [start] rmiregistry <port>
        // Linux: rmiregistry <port> [&]
        // Az rmiregistry-t ugy kell inditani, hogy lassan a SzolgaltatasSzerver classait!
        // A mellekelt projektben, ideaval ez a lesson-rmi/out/production/classes konyvtar

        int port = 1009;

        if(argv.length >= 1 && argv[0].equals("--with-registry")) {
            try {
                if(argv.length >= 2) {
                    port = Integer.parseInt(argv[1]);
                }
                java.rmi.registry.LocateRegistry.createRegistry(port);
                System.out.println("RMI registry ready.");
            } catch (Exception e) {
                System.out.println("Exception starting RMI registry:");
                e.printStackTrace();
                return;
            }
        } else if (argv.length >= 1) {
            port = Integer.parseInt(argv[0]);
        }

        System.out.println("Using port: " + port);

        try {

            String name = "Szolgaltatas";
            Szolgaltatas engine = new SzolgaltatasImpl();
            Szolgaltatas stub =
                    (Szolgaltatas) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry(port);
            registry.rebind(name, stub);
            System.out.println("Szolgaltatas bound");
        } catch (Exception e) {
            System.err.println("Szolgaltatas exception:");
            e.printStackTrace();
        }
    }
}
