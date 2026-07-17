package com.vehiclerental.observer;

import java.util.ArrayList;
import java.util.List;
/**
 * The NotificationManager class manages notification
 * observers and sends notification messages to all
 * registered observers.
 */
public class NotificationManager {

    private final List<NotificationObserver> observers = new ArrayList<>();

    public void addObserver(NotificationObserver observer) {

        observers.add(observer);

    }

    public void removeObserver(NotificationObserver observer) {

        observers.remove(observer);

    }

    public void notifyObservers(String message) {

        for (NotificationObserver observer : observers) {
            observer.update(message);
        }

    }

}