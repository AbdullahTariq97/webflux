package uk.sky.fodmap.util;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DefaultSubscriber<T> implements Subscriber<T> {

    private String name = "";

    public DefaultSubscriber(String name) {
        this.name = name + " ";
    }

    public DefaultSubscriber() {
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T item) {
        System.out.println(name + "received: " + item);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(name + " : Error : " +  throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println(name + "Completed");
    }
}
