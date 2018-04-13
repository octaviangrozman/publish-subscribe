package test;

import broker.Broker;
import publisher.Publisher;
import shared.Event;
import shared.EventType;
import shared.Movie;
import subscriber.Subscriber;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Broker.run();

        // publisher for adding movies = John
        Publisher publisherJohn = Publisher.run("John", EventType.ADD_MOVIE);

        // publisher for removing movies = Destructor
        Publisher publisherDestructor = Publisher.run("Destructor", EventType.REMOVE_MOVIE);

        // publisher for adding, removing and updating movies = Boss
        Publisher publisherBoss = Publisher.run("Boss", EventType.ADD_MOVIE, EventType.REMOVE_MOVIE, EventType.UPDATE_MOVIE);

        Subscriber subscriberEmy = Subscriber.run("Emy");
        subscriberEmy.subscribe(EventType.ADD_MOVIE);

        Subscriber subscriberDonn = Subscriber.run("Donn");
        subscriberDonn.subscribe(EventType.REMOVE_MOVIE);

        Subscriber subscriberAlex = Subscriber.run("Alex");
        subscriberAlex.subscribe(EventType.UPDATE_MOVIE);


        publisherJohn.publish(new Event(EventType.ADD_MOVIE, new Movie("Killer", "author1", new Date(1,2, 1999), 100000)));
    }
}
