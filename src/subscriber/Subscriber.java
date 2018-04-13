package subscriber;

import shared.EventType;
import shared.RemoteBroker;
import shared.RemoteSubscriber;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static java.lang.System.out;

public class Subscriber extends UnicastRemoteObject implements RemoteSubscriber {

    private String name;
    private RemoteBroker broker;

    Subscriber(String name, RemoteBroker broker) throws RemoteException {
        this.name = name;
        this.broker = broker;
    }

    public void subscribe(EventType... eventTypes) throws RemoteException {
        broker.subscribe(this, eventTypes);
    }


    @Override
    public void notify(EventType eventType) throws RemoteException {
        out.println(name + " got notifications about the following event: " + eventType);
    }

    public static Subscriber run(String name) throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry(RemoteBroker.PORT);
        RemoteBroker broker = (RemoteBroker) registry.lookup(RemoteBroker.SERVER_NAME);
        return new Subscriber(name, broker);
    }
}
