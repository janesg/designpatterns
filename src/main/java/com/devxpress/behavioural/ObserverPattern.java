package com.devxpress.behavioural;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer - Behavioural
 *
 * Used to implement distributed event handling systems, in "event driven" software
 *
 * An object, called the subject, maintains a list of its dependents, called observers, and
 * notifies them automatically of any state changes, usually by calling one of their methods.
 *
 * Recognizeable by behavioral methods which invokes a method on an instance of another
 * abstract/interface type, depending on own state
 *
 * Usage examples in Java APIs:
 *
 * java.util.Observer/java.util.Observable (rarely used in real world though)
 *
 * All implementations of java.util.EventListener (practically all over Swing thus)
 *
 * javax.servlet.http.HttpSessionBindingListener
 * javax.servlet.http.HttpSessionAttributeListener
 * javax.faces.event.PhaseListener
 */

public class ObserverPattern {

    public static void main(String[] args) {
        //create subject
        MyTopic topic = new MyTopic("Random Message");

        //create observers
        Observer ob1 = new MyTopicSubscriber("Observer 1");
        Observer ob2 = new MyTopicSubscriber("Observer 2");
        Observer ob3 = new MyTopicSubscriber("Observer 3");

        //register observers to the subject
        topic.register(ob1);
        topic.register(ob2);
        topic.register(ob3);

        //attach observer to subject
        ob1.setSubject(topic);
        ob2.setSubject(topic);
        ob3.setSubject(topic);

        //check if any update is available
        ob1.update();

        //now send message to subject
        topic.postMessage("New Message 1");

        //check if any update is available
        ob2.update();

        // unregister an Observer
        topic.unregister(ob2);

        topic.postMessage("New Message 2");
    }
}

// Subject also sometimes referred to as Observable
interface Subject {

    //methods to register and unregister observers
    void register(Observer obj);
    void unregister(Observer obj);

    //method to notify observers of change
    void notifyObservers();

    //method to get updates from subject
    Object getUpdate();
}

interface Observer {

    // method to update the observer, invoked by subject
    // Note: there are 2 separate actions in this implementation:
    //  - Subject notifying Observer of change
    //  - Observer requesting changed state from Subject
    //
    // This could be combined by passing the state as argument to update method
    void update();

    //attach with subject to observe
    void setSubject(Subject sub);
}

// The concrete Subject implementation
class MyTopic implements Subject {

    private List<Observer> observers;
    private String name;
    private String message; // This is the observable state
    private boolean changed;
    private final Object MUTEX = new Object();

    public MyTopic(String nm) {
        this.name = nm;
        this.observers=new ArrayList<>();
    }

    @Override
    public void register(Observer obj) {

        if (obj == null) {
            throw new NullPointerException("Null Observer");
        }

        synchronized (MUTEX) {
            if (!observers.contains(obj)) {
                observers.add(obj);
            }
        }
    }

    @Override
    public void unregister(Observer obj) {
        synchronized (MUTEX) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObservers() {

        List<Observer> observersLocal = null;

        // synchronization is used to make sure any observer registered
        // after message is received is not notified
        synchronized (MUTEX) {
            if (!changed) {
                return;
            }

            observersLocal = new ArrayList<>(this.observers);
            this.changed = false;
        }

        for (Observer observer : observersLocal) {
            observer.update();
        }
    }

    @Override
    public Object getUpdate() {
        return this.message;
    }

    //method to post message to the topic
    public void postMessage(String msg) {
        System.out.println("Message Posted to Topic <" + this.name + "> : " + msg);
        this.message = msg;
        this.changed = true;
        notifyObservers();
    }
}

// The concrete Observer implementation
class MyTopicSubscriber implements Observer {

    private String name;
    private Subject topic;

    public MyTopicSubscriber(String nm){
        this.name = nm;
    }

    @Override
    public void update() {
        String msg = (String) topic.getUpdate();
        if (msg == null) {
            System.out.println(name + " :: No new message");
        } else {
            System.out.println(name + " :: Consuming message :: " + msg);
        }
    }

    @Override
    public void setSubject(Subject sub) {
        this.topic = sub;
    }

}