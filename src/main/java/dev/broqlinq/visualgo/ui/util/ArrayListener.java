package dev.broqlinq.visualgo.ui.util;

import java.util.EventListener;

public interface ArrayListener<T> extends EventListener {

    void onArrayChanged(ArrayEvent<T> e);
}
