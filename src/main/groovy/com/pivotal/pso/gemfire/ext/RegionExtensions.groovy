package com.pivotal.pso.gemfire.ext

import static groovy.lang.Closure.DELEGATE_FIRST
import groovy.transform.CompileStatic

import com.gemstone.gemfire.cache.Region
import com.gemstone.gemfire.cache.util.CacheListenerAdapter
import com.gemstone.gemfire.cache.util.CacheWriterAdapter
import com.pivotal.pso.gemfire.util.CacheListenerBuilder
import com.pivotal.pso.gemfire.util.CacheListenerSupport
import com.pivotal.pso.gemfire.util.CacheWriterBuilder
import com.pivotal.pso.gemfire.util.CacheWriterSupport


@CompileStatic
class RegionExtensions {

    static Region cacheListener(Region selfType, Map<String, Closure> funcmap) {

        Map<String, Closure> listener = [:]

        // create a new map, add & transform closures in it
        funcmap.collectEntries(listener) { String name, Closure closure ->
            def owner = closure.owner
            def thisObject = closure.thisObject
            def cls = new CacheListenerSupport()
            def hydrated = closure.rehydrate(cls, owner, thisObject)
            hydrated.resolveStrategy = Closure.DELEGATE_FIRST
            [name, hydrated]
        }

        selfType.getAttributesMutator().addCacheListener(listener as CacheListenerAdapter)
        selfType
    }

    static Region leftShift(Region selfType, @DelegatesTo(strategy=DELEGATE_FIRST, value=CacheListenerBuilder) Closure closure) {

        def builder = new CacheListenerBuilder()
        def hydrated = closure.rehydrate(builder, selfType, selfType)
        hydrated.resolveStrategy = DELEGATE_FIRST
        hydrated()

        def listener = builder.build()
        selfType.getAttributesMutator().addCacheListener(listener as CacheListenerAdapter)
        selfType
    }

    static Region cacheWriter(Region selfType, Map<String, Closure> funcmap) {

        Map<String, Closure> listener = [:]

        // create a new map, add & transform closures in it
        funcmap.collectEntries(listener) { String name, Closure closure ->
            def owner = closure.owner
            def thisObject = closure.thisObject
            def cls = new CacheWriterSupport()
            def hydrated = closure.rehydrate(cls, owner, thisObject)
            hydrated.resolveStrategy = Closure.DELEGATE_FIRST
            [name, hydrated]
        }

        selfType.getAttributesMutator().setCacheWriter(listener as CacheWriterAdapter)
        selfType
    }

    static Region rightShift(Region selfType, @DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterBuilder) Closure closure) {

        def builder = new CacheWriterBuilder()
        def hydrated = closure.rehydrate(builder, selfType, selfType)
        hydrated.resolveStrategy = DELEGATE_FIRST
        hydrated()

        def listener = builder.build()
        selfType.getAttributesMutator().setCacheWriter(listener as CacheWriterAdapter)
        selfType
    }
}
