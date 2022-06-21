package com.example.rxjavaapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableSubscriber
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.reactivestreams.Subscription


class MainActivity : AppCompatActivity() {

    private val TAG = "TASK"
    var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskObservable: Observable<Task> = Observable
            .fromIterable(DataSource.createTasksList())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object:Observer<Task> {
            override fun onSubscribe(d: Disposable) {
                disposables.add(d)
            }
            override fun onNext(task: Task) {
                Log.d(TAG, "onNext: : " + task.description)
            }
            override fun onError(e: Throwable) {
            }
            override fun onComplete() {
            }
        })

        Flowable.range(0, 1000000)
            .onBackpressureBuffer()
            .observeOn(Schedulers.computation())
            .subscribe(object : FlowableSubscriber<Int> {
                override fun onSubscribe(s: Subscription?) {}
                override fun onNext(integer: Int) {
                    Log.d(TAG, "onNext: $integer")
                }

                override fun onError(t: Throwable) {
                    Log.e(TAG, "onError: ", t)
                }

                override fun onComplete() {}
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}