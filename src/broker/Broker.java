package broker;

import shared.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Broker extends UnicastRemoteObject implements RemoteBroker {

    private List<Movie> movies;
    private Map<EventType, ArrayList<RemoteSubscriber>> eventSubscribersMap;

    protected Broker() throws RemoteException {
        movies = new ArrayList<>();
        eventSubscribersMap = new HashMap<>();
        for (EventType eventType : EventType.values()) {
            eventSubscribersMap.put(eventType, new ArrayList<>());
        }
    }


    @Override
    public void addMovie(Movie movie) throws RemoteException {
        movies.add(movie);
    }

    @Override
    public void removeMovie(Movie movie) throws RemoteException {
        movies.remove(movie);
    }

    @Override
    public void updateMovie(Movie movie) throws RemoteException {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId() == movie.getId()) {
                Movie foundMovie = movies.get(i);
                foundMovie.setAuthor(movie.getAuthor());
                foundMovie.setDateCreated(movie.getDateCreated());
                foundMovie.setDurationInSec(movie.getDurationInSec());
                foundMovie.setName(movie.getName());
            }
        }
    }

    @Override
    public void publish(Event event) throws RemoteException {
        switch (event.getEventType()) {
            case ADD_MOVIE: addMovie(event.getPayload()); break;
            case REMOVE_MOVIE: removeMovie(event.getPayload()); break;
            case UPDATE_MOVIE: updateMovie(event.getPayload()); break;
            default: return;
        }
        notifySubscribers(event.getEventType());
    }

    @Override
    public void subscribe(RemoteSubscriber subscriber, EventType[] eventTypes) {
        for (EventType eventType:eventTypes) {
            ArrayList<RemoteSubscriber> subscribers = eventSubscribersMap.get(eventType);
            if (!subscribers.contains(subscriber)) {
                subscribers.add(subscriber);
                eventSubscribersMap.put(eventType, subscribers);
            }
        }
    }

    @Override
    public void unsubscribe(RemoteSubscriber subscriber, EventType[] eventTypes) throws RemoteException {
        for (EventType eventType:eventTypes) {
            ArrayList<RemoteSubscriber> subscribers = eventSubscribersMap.get(eventType);
            if (subscribers.contains(subscriber)) {
                subscribers.remove(subscriber);
                eventSubscribersMap.put(eventType, subscribers);
            }
        }
    }

    private void notifySubscribers(EventType eventType) throws RemoteException {
        ArrayList<RemoteSubscriber> subscribers = eventSubscribersMap.get(eventType);
        for (RemoteSubscriber subscriber: subscribers) {
            subscriber.notify(eventType);
        }
    }

    public static void run() throws RemoteException {
        Broker broker = new Broker();
        Registry registry = LocateRegistry.createRegistry(PORT);
        registry.rebind(SERVER_NAME, broker);
        System.out.println("Broker started...");
    }
}
