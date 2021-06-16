package com.bugsnag.android

import com.bugsnag.android.internal.StateObserver
import java.util.concurrent.CopyOnWriteArrayList

internal open class BaseObservable {

    private val observers = CopyOnWriteArrayList<StateObserver>()

    /**
     * Adds an observer that can react to [StateEvent] messages.
     */
    fun addObserver(observer: StateObserver) {
        observers.addIfAbsent(observer)
    }

    /**
     * Removes a previously added observer that reacts to [StateEvent] messages.
     */
    fun removeObserver(observer: StateObserver) {
        observers.remove(observer)
    }

    /**
     * This method should be invoked when the notifier's state has changed. If an observer
     * has been set, it will be notified of the [StateEvent] message so that it can react
     * appropriately. If no observer has been set then this method will no-op.
     */
    fun updateState(provider: () -> StateEvent) {
        // optimization to avoid unnecessary iterator and StateEvent construction
        if (observers.isEmpty()) {
            return
        }

        // construct the StateEvent object and notify observers
        val event = provider()
        observers.forEach { it.onStateChange(event) }
    }
}
