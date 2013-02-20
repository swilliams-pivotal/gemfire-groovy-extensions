package com.pivotal.pso.gemfire.util

import static groovy.lang.Closure.DELEGATE_FIRST
import groovy.transform.CompileStatic

import com.gemstone.gemfire.cache.util.CacheWriterAdapter


@CompileStatic
class CacheWriterBuilder {

    Map<String, Closure> listener = [:]

    def beforeCreate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeCreate'] = hydrateAndDelegate(closure)
        this
    }

    def beforeUpdate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeUpdate'] = hydrateAndDelegate(closure)
        this
    }

    def beforeInvalidate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeInvalidate'] = hydrateAndDelegate(closure)
        this
    }

    def beforeDestroy(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeDestroy'] = hydrateAndDelegate(closure)
        this
    }

    def beforeRegionCreate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeRegionCreate'] = hydrateAndDelegate(closure)
        this
    }

    def beforeRegionClear(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeRegionClear'] = hydrateAndDelegate(closure)
        this
    }

    def beforeRegionInvalidate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeRegionInvalidate'] = hydrateAndDelegate(closure)
        this
    }

    def beforeRegionDestroy(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeRegionDestroy'] = hydrateAndDelegate(closure)
        this
    }

    def beforeRegionLive(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeRegionLive'] = hydrateAndDelegate(closure)
        this
    }

    def build() {
        listener as CacheWriterAdapter
    }

    static Closure hydrateAndDelegate(Closure closure) {
        def cws = new CacheWriterSupport()
        def owner = closure.owner
        def thisObject = closure.thisObject
        def hydrated = closure.rehydrate(cws, owner, thisObject)
        hydrated.resolveStrategy = DELEGATE_FIRST
        hydrated
    }

}
