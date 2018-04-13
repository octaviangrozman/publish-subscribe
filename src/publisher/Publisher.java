package publisher;

import shared.Event;
import shared.EventType;
import shared.RemoteBroker;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

public class Publisher extends UnicastRemoteObject implements Remote {

    private String name;
    private List<EventType> eventTypes;
    private RemoteBroker broker;

    Publisher(String name, List<EventType> eventTypes, RemoteBroker broker) throws RemoteException {
        this.name = name;
        this.broker = broker;
        this.eventTypes = eventTypes;
    }

    public void publish(Event event) throws RemoteException {
        if (!eventTypes.contains(event.getEventType())) throw new IllegalArgumentException("This publisher does not handle that type of event: " + event.getEventType());
        broker.publish(event);
    }

    public static Publisher run(String name, EventType... eventTypes) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(RemoteBroker.PORT);
        RemoteBroker broker = (RemoteBroker) registry.lookup(RemoteBroker.SERVER_NAME);
        return new Publisher(name, Arrays.asList(eventTypes), broker);
    }
}
