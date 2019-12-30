package com.example.example2introducingdisposable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


//huy bo dang ky de tranh bi ro ri bo nho khi Activity/Fragment bi destroy.
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> nameObservable = getNameObservable();

        Observer<String> nameObserver = getNameObserver();

        //Observer dang ky Observable
        nameObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nameObserver);


    }

    private Observer<String> getNameObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }

    private Observable<String> getNameObservable(){
        return Observable.just("Quang Hai", "Cong Phuong", "van Hau", "Van Lam");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //huy bo dang ky de tranh bi ro ri bo nho khi Activity/Fragment bi destroy.
        disposable.dispose();
    }
}
