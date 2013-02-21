package com.pivotal.pso.gemfire.util

import groovy.transform.CompileStatic

import com.gemstone.gemfire.cache.CacheWriterException
import com.gemstone.gemfire.cache.EntryEvent
import com.gemstone.gemfire.cache.RegionEvent
import com.gemstone.gemfire.cache.util.CacheWriterAdapter


@CompileStatic
class ClosureCacheWriterAdapter<K, V> extends CacheWriterAdapter<K, V> {

    private final Map<String, Closure> funcmap

    public ClosureCacheWriterAdapter(java.util.Map<String, groovy.lang.Closure> funcmap) {
        this.funcmap = funcmap
    }

    private void call(Closure closure, EntryEvent<K, V> event) throws CacheWriterException {
        def hydrated = closure.rehydrate(closure.delegate, this, this)
        hydrated.call(event)
    }

    @Override
    public void beforeCreate(EntryEvent<K, V> event)
            throws CacheWriterException {
        def closure = funcmap['beforeCreate']
        if (closure) call(closure, event)
    }

    @Override
    public void beforeUpdate(EntryEvent<K, V> event)
            throws CacheWriterException {
        funcmap['beforeUpdate']?.call(event)
    }

    @Override
    public void beforeDestroy(EntryEvent<K, V> event)
            throws CacheWriterException {
        funcmap['beforeDestroy']?.call(event)
    }

    @Override
    public void beforeRegionClear(RegionEvent<K, V> event)
            throws CacheWriterException {
        funcmap['beforeRegionClear']?.call(event)
    }

    @Override
    public void beforeRegionDestroy(RegionEvent<K, V> event)
            throws CacheWriterException {
        funcmap['beforeRegionDestroy']?.call(event)
    }

    @Override
    public void close() {
        funcmap['close']?.call()
    }

    

}
