package test;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class OtherRemoteImpl extends UnicastRemoteObject implements OtherRemote {

    int callCount = 0;

    OtherRemoteImpl() throws RemoteException {
        super();
    }

    @Override
    public String foo() throws RemoteException {
        callCount++;
        return "bar " + callCount;
    }
}
