package test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Szolgaltatas extends Remote {

    int getNumber() throws RemoteException;

}
