package uk.sky.fodmap.util;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;

import java.util.function.Consumer;

public class Util {

    private static final Faker FAKER = Faker.instance();

    public static Consumer<Object> onNext(){
        return obj -> System.out.println("Recieved: " + obj);
    }

    public static Consumer<Throwable> onError(){
        return e -> System.out.println("Error:" + e.getMessage());
    }

    public static Runnable onComplete(){
        return () -> System.out.println("Completed");
    }

    public static Faker faker(){
        return FAKER;
    }

    public static void sleepSeconds(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Subscriber<Object> subscriber(){
        return new DefaultSubscriber<Object>();
    }
    public static Subscriber<Object> subscriber(String subscriberName){
        return new DefaultSubscriber<Object>(subscriberName);
    }
}
