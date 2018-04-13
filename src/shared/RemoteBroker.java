package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteBroker extends Remote {

    int PORT = 1099;
    String SERVER_NAME = "broker";

    void addMovie(Movie movie) throws RemoteException;
    void removeMovie(Movie movie) throws RemoteException;
    void updateMovie(Movie movie) throws RemoteException;

    void publish(Event event) throws RemoteException;

    void subscribe(RemoteSubscriber subscriber, EventType[] eventTypes) throws RemoteException;
    void unsubscribe(RemoteSubscriber subscriber, EventType[] eventTypes) throws RemoteException;
}
