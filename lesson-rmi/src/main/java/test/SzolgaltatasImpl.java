package test;

import java.rmi.RemoteException;

public class SzolgaltatasImpl implements Szolgaltatas {

    private OtherRemote otherRemoteObject;

    @Override
    public int getNumber() {
        return 42;
    }

    @Override
    public OtherRemote getRemoteObject() throws RemoteException {
        if (otherRemoteObject == null) {
            otherRemoteObject = new OtherRemoteImpl();
        }
        return otherRemoteObject;
    }
}
