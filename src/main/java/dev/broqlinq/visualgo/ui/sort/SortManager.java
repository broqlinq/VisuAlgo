package dev.broqlinq.visualgo.ui.sort;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SortManager {

    private final Lock taskLock = new ReentrantLock();
    private final SwingPropertyChangeSupport propertySupport;
    private volatile Status status;
    private SortingTask<?> activeTask;

    public SortManager() {
        this.status = Status.IDLE;
        this.propertySupport = new SwingPropertyChangeSupport(this);
    }

    public boolean submit(SortingTask<?> task) {
        if (!taskLock.tryLock()) {
            return false;
        }
        try {
            if (status != Status.IDLE) {
                return false;
            }
            status = Status.RUNNING;
            activeTask = new DelegatingTask<>(task);
            activeTask.execute();
            propertySupport.firePropertyChange("status", Status.IDLE, Status.RUNNING);
        } finally {
            taskLock.unlock();
        }
        return true;
    }

    public boolean isTaskRunning() {
        return status == Status.RUNNING;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    public void cancel() {
        if (activeTask != null) {
            if (!taskLock.tryLock()) {
                return;
            }
            try {
                activeTask.cancel(true);
            } finally {
                taskLock.unlock();
            }
        }
    }

    public enum Status {IDLE, RUNNING}

    private class DelegatingTask<N extends Number & Comparable<N>> extends SortingTask<N> {

        private final SortingTask<N> delegate;

        public DelegatingTask(SortingTask<N> delegate) {
            super(delegate.chart, delegate.pauseTimeMillis, TimeUnit.MILLISECONDS);
            this.delegate = delegate;
        }

        @Override
        protected Void doInBackground() throws Exception {
            return delegate.doInBackground();
        }

        @Override
        protected void done() {
            assert status == Status.RUNNING;
            taskLock.lock();
            try {
                status = Status.IDLE;
                activeTask = null;
                propertySupport.firePropertyChange("status", Status.RUNNING, Status.IDLE);
            } finally {
                taskLock.unlock();
            }
        }
    }
}
