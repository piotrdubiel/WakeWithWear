package rx.wear.connect;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

public class OnSubscribeWearableConnection implements Observable.OnSubscribe<ConnectionEvent> {
    private final GoogleApiClient googleApiClient;

    public OnSubscribeWearableConnection(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void call(final Subscriber<? super ConnectionEvent> observer) {

        final GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                if (!observer.isUnsubscribed()) {
                    observer.onNext(new Connected(bundle));
                    observer.onCompleted();
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                if (!observer.isUnsubscribed()) {
                    observer.onNext(new Suspended(i));
                    observer.onCompleted();
                }
            }
        };
        final GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                if (!observer.isUnsubscribed()) {
                    observer.onNext(new Failed(connectionResult));
                    observer.onCompleted();
                }
            }
        };

        final Subscription subscription = Subscriptions.create(new Action0() {
            @Override
            public void call() {
                googleApiClient.unregisterConnectionCallbacks(connectionCallbacks);
                googleApiClient.unregisterConnectionFailedListener(connectionFailedListener);
            }
        });

        googleApiClient.registerConnectionCallbacks(connectionCallbacks);
        googleApiClient.registerConnectionFailedListener(connectionFailedListener);
        observer.add(subscription);
        googleApiClient.connect();
    }
}
