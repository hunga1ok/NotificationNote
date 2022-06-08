package com.huncorp.myday.notinote.base

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel<V : BaseView> : ViewModel(), LifecycleObserver {
    protected lateinit var v: V
    protected var disposable = CompositeDisposable()
    private val TAG = this.javaClass.simpleName
    protected var myLifecycle: LifecycleOwner? = null

    private var observeJob: ArrayList<Job?> = arrayListOf()

    fun attachView(v: V?) {
        if (v != null) {
            this.v = v
        } else
            throw NullPointerException("presenter: please attach your view on    onCreate() method.")

    }

    open fun setLifecycle(lifecycle: LifecycleOwner?) {
        lifecycle?.let {
            myLifecycle = it
            it.lifecycle.addObserver(this)
        }
    }

    fun getLifecycle() = myLifecycle!!

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any?) =
        v.getCurrentContext()?.let {
            if (formatArgs.isEmpty()) it.getString(resId)
            else it.getString(resId, *formatArgs)
        }

    open fun onReady() {
        /* override when needed */
    }

    open fun refresh() {
        /* override when needed */
    }

    protected fun subscribe(job: () -> Disposable) {
        disposable.add(job())
    }

    protected fun observe(job: suspend () -> Unit) {
        observeJob.add(
            viewModelScope.launch(Dispatchers.Main) {
                job.invoke()
            }
        )
    }

    protected fun log(message: Any?) {
        Log.i(TAG, message.toString())
    }

    protected fun longLog(message: Any?) {
        Log.i(TAG, message.toString())
    }

    open fun detachView() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onViewDestroy() {
        detachView()
    }

    /**
     * To cancel all running observables, call it if ex. needed to refresh and ignore previous results
     */
    protected fun cancelObservables() {
        observeJob.forEach {
            try {
                it?.cancel()
            } catch (e: Exception) {
                /*  ignore if  error */
            }
        }
    }

    /**
     * To cancel all running RX disposable, call it if ex. needed to refresh and ignore previous results
     */
    protected fun clearDisposable() {
        disposable.clear()
    }

    override fun onCleared() {
        cancelObservables()
        disposable.dispose()
        super.onCleared()
    }
}