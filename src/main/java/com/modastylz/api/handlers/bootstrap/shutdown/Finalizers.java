package com.modastylz.api.handlers.bootstrap.shutdown;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.modastylz.api.handlers.infra.CADataSourceRegistry;

public class Finalizers implements Iterable<Finalizer> {

    private final Iterator<Finalizer> internalIterator;

    public Finalizers() {
        final List<Finalizer> finalizers = new ArrayList<>();
        finalizers.add(CADataSourceRegistry.getInstance());
        internalIterator = finalizers.iterator();
    }

    @Override
    public Iterator<Finalizer> iterator() {
        return new Iterator<Finalizer>() {

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public Finalizer next() {
                return internalIterator.next();
            }

        };
    }

}
