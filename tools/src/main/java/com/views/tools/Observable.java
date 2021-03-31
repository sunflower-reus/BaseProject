package com.views.tools;

import java.io.Serializable;

/**
 * Description:
 */
@SuppressWarnings("unused")
public class Observable<T> implements Serializable {
    private T mValue;

    /**
     * Wraps the given object and creates an observable object
     *
     * @param value
     *         The value to be wrapped as an observable.
     */
    public Observable(T value) {
        mValue = value;
    }

    /**
     * Creates an empty observable object
     */
    public Observable() {
    }

    /**
     * @return the stored value.
     */
    public T get() {
        return mValue;
    }

    /**
     * Set the stored value.
     */
    public void set(T value) {
        if (value != mValue) {
            mValue = value;
        }
    }
}