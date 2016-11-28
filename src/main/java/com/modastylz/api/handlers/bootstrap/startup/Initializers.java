package com.modastylz.api.handlers.bootstrap.startup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.modastylz.api.handlers.infra.CADataSourceRegistry;
import com.modastylz.api.handlers.infra.ConfigRegistry;

public class Initializers implements Iterable<Initializer> {
    private final Iterator<Initializer> internalIterator;

    public Initializers() {
        final List<Initializer> initializers = new ArrayList<>();
        initializers.add(CADataSourceRegistry.getInstance());
        initializers.add(ConfigRegistry.instance());
        internalIterator = initializers.iterator();
    }

    @Override
    public Iterator<Initializer> iterator() {
        return new Iterator<Initializer>() {

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public Initializer next() {
                return internalIterator.next();
            }

        };
    }

}
