package com.example.qapitalinterview.utils;


import java.util.HashSet;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.plugins.RxJavaObservableExecutionHook;
import rx.plugins.RxJavaPlugins;

public class TestObservable<T> extends Observable<T> {

    private static final RxJavaObservableExecutionHook hook = RxJavaPlugins.getInstance().getObservableExecutionHook();

    private TestOnSubscribe<T> testOnSubscribe;

    /**
     * Creates an Observable with a Function to execute when it is subscribed to.
     * <p>
     * <em>Note:</em> Use {@link #create(OnSubscribe)} to create an Observable, instead of this constructor,
     * unless you specifically have a need for inheritance.
     *
     * @param f {@link OnSubscribe} to be executed when {@link #subscribe(Subscriber)} is called
     */
    protected TestObservable(rx.Observable.OnSubscribe f) {
        super(f);
        testOnSubscribe = (TestOnSubscribe<T>) f;
    }

    public void onNext(T object) {
        testOnSubscribe.putNext(object);
    }

    public void onError(Throwable throwable) {
        testOnSubscribe.putError(throwable);
    }

    public static<T> TestObservable<T> testObservable() {
        return new TestObservable<>(hook.onCreate(new TestOnSubscribe<T>()));
    }

    private static class TestOnSubscribe<T> implements Observable.OnSubscribe<T> {

        private Set<Subscriber> subscribers = new HashSet<>();

        @Override
        public void call(Subscriber<? super T> subscriber) {
            subscribers.add(subscriber);
        }

        public void putNext(T object) {
            for (Subscriber subscriber : subscribers) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(object);
                }
            }
        }

        public void putError(Throwable throwable) {
            for (Subscriber subscriber : subscribers) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(throwable);
                }
            }
        }
    }
}
