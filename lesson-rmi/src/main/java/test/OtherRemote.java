package test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OtherRemote extends Remote {

    String foo() throws RemoteException;
}
