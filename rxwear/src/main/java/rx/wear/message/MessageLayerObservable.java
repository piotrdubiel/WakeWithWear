package rx.wear.message;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.AndroidSubscriptions;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class MessageLayerObservable {
    private final GoogleApiClient googleApiClient;

    public MessageLayerObservable(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    public Observable<MessageApi.SendMessageResult> sendMessage(final String path) {
        return sendMessage(path, new byte[0]);
    }

    public Observable<MessageApi.SendMessageResult> sendMessage(final String path, final byte[] bytes) {
        return Observable.create(new Observable.OnSubscribe<MessageApi.SendMessageResult>() {
            @Override
            public void call(final Subscriber<? super MessageApi.SendMessageResult> subscriber) {

                NodeApi.GetConnectedNodesResult nodes =
                        Wearable.NodeApi.getConnectedNodes(googleApiClient).await();

                for (Node node : nodes.getNodes()) {
                    Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), path, bytes)
                            .setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                                @Override
                                public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                    subscriber.onNext(sendMessageResult);
                                    subscriber.onCompleted();
                                }
                            });
                }

            }
        }).subscribeOn(Schedulers.newThread()).timeout(5, TimeUnit.SECONDS);
    }

    public Observable<MessageEvent> onMessage() {
        return Observable.create(new Observable.OnSubscribe<MessageEvent>() {
            @Override
            public void call(final Subscriber<? super MessageEvent> observer) {
                final MessageApi.MessageListener messageListener = new MessageApi.MessageListener() {
                    @Override
                    public void onMessageReceived(MessageEvent messageEvent) {
                        observer.onNext(messageEvent);
                        observer.onCompleted();
                    }
                };

                final Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(new Action0() {
                    @Override
                    public void call() {
                        Wearable.MessageApi.removeListener(googleApiClient, messageListener);
                    }
                });

                Wearable.MessageApi.addListener(googleApiClient, messageListener);
                observer.add(subscription);
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
