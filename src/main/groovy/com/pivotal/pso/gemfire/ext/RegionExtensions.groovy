package com.pivotal.pso.gemfire.ext

import groovy.transform.CompileStatic

import com.gemstone.gemfire.cache.Region
import com.gemstone.gemfire.cache.util.CacheListenerAdapter
import com.gemstone.gemfire.cache.util.CacheWriterAdapter
import com.pivotal.pso.gemfire.util.CacheListenerSupport
import com.pivotal.pso.gemfire.util.CacheWriterSupport


@CompileStatic
class RegionExtensions {

    static Region addCacheListener(Region selfType, Map<String, Closure> funcmap) {

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

    static Region setCacheWriter(Region selfType, Map<String, Closure> funcmap) {

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

}
