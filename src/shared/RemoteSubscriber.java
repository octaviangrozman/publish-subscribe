package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSubscriber extends Remote {
    void notify(EventType eventType) throws RemoteException;
}
