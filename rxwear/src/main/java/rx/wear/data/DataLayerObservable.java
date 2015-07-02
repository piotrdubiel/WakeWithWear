package rx.wear.data;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.wear.connect.ConnectionEvent;
import rx.wear.connect.OnSubscribeWearableConnection;

public class DataLayerObservable {
    private final GoogleApiClient googleApiClient;

    public DataLayerObservable(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    public Observable<ConnectionEvent> connect() {
        return Observable.create(new OnSubscribeWearableConnection(googleApiClient))
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<DataApi.DataItemResult> sendData(final PutDataRequest data) {
        return Observable.create(new Observable.OnSubscribe<DataApi.DataItemResult>() {
            @Override
            public void call(final Subscriber<? super DataApi.DataItemResult> observer) {
                Wearable.DataApi.putDataItem(googleApiClient, data).setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {
                        observer.onNext(dataItemResult);
                        observer.onCompleted();
                    }
                });
            }
        });
    }

    public Observable<DataEvent> onData() {
        return Observable.create(new Observable.OnSubscribe<DataEvent>() {
            @Override
            public void call(final Subscriber<? super DataEvent> subscriber) {
                DataApi.DataListener dataListener = new DataApi.DataListener() {
                    @Override
                    public void onDataChanged(DataEventBuffer dataEvents) {
                        ArrayList<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
                        dataEvents.release();
                        for (DataEvent event : events) {
                            subscriber.onNext(event);
                        }
                        subscriber.onCompleted();
                    }
                };

                Wearable.DataApi.addListener(googleApiClient, dataListener);
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
