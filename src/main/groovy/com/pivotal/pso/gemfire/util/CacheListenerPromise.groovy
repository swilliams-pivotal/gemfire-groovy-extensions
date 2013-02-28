package com.pivotal.pso.gemfire.util

import groovyx.gpars.actor.impl.MessageStream
import groovyx.gpars.dataflow.Promise
import groovyx.gpars.group.PGroup
import groovyx.gpars.scheduler.Pool

import java.util.concurrent.TimeUnit

import com.gemstone.gemfire.cache.EntryEvent
import com.gemstone.gemfire.cache.util.CacheListenerAdapter


class CacheListenerPromise<K, V> extends CacheListenerAdapter<K, V> implements
        Promise<EntryEvent<K, V>> {

    @Override
    public EntryEvent<K, V> get() throws Throwable {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EntryEvent<K, V> get(long timeout, TimeUnit units) throws Throwable {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void join() throws InterruptedException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void join(long timeout, TimeUnit units) throws InterruptedException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <V> Promise<V> rightShift(Closure<V> closure) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <V> void whenBound(Closure<V> closure) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <V> void whenBound(Pool pool, Closure<V> closure) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <V> void whenBound(PGroup group, Closure<V> closure) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void whenBound(MessageStream stream) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <V> Promise<V> then(Closure<V> closure) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <V> Promise<V> then(Pool pool, Closure<V> closure) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <V> Promise<V> then(PGroup group, Closure<V> closure) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <V> Promise<V> then(Closure<V> closure, Closure<V> errorHandler) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <V> Promise<V> then(Pool pool, Closure<V> closure,
            Closure<V> errorHandler) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <V> Promise<V> then(PGroup group, Closure<V> closure,
            Closure<V> errorHandler) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isBound() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isError() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Throwable getError() {
        // TODO Auto-generated method stub
        return null;
    }

}
