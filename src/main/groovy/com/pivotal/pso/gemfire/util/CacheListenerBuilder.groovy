package com.pivotal.pso.gemfire.util

import static groovy.lang.Closure.DELEGATE_FIRST
import groovy.transform.CompileStatic

import com.gemstone.gemfire.cache.util.CacheListenerAdapter


@CompileStatic
class CacheListenerBuilder {

    Map<String, Closure> listener = [:]

    def afterCreate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheListenerSupport) Closure closure) {
        listener['afterCreate'] = hydrateAndDelegate(closure)
        this
    }

    def afterUpdate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheListenerSupport) Closure closure) {
        listener['afterUpdate'] = hydrateAndDelegate(closure)
        this
    }

    def afterInvalidate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheListenerSupport) Closure closure) {
        listener['afterInvalidate'] = hydrateAndDelegate(closure)
        this
    }

    def afterDestroy(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheListenerSupport) Closure closure) {
        listener['afterDestroy'] = hydrateAndDelegate(closure)
        this
    }

    def afterRegionCreate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheListenerSupport) Closure closure) {
        listener['afterRegionCreate'] = hydrateAndDelegate(closure)
        this
    }

    def afterRegionClear(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheListenerSupport) Closure closure) {
        listener['afterRegionClear'] = hydrateAndDelegate(closure)
        this
    }

    def afterRegionInvalidate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheListenerSupport) Closure closure) {
        listener['afterRegionInvalidate'] = hydrateAndDelegate(closure)
        this
    }

    def afterRegionDestroy(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheListenerSupport) Closure closure) {
        listener['afterRegionDestroy'] = hydrateAndDelegate(closure)
        this
    }

    def afterRegionLive(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheListenerSupport) Closure closure) {
        listener['afterRegionLive'] = hydrateAndDelegate(closure)
        this
    }

    def build() {
        listener as CacheListenerAdapter
    }

    static Closure hydrateAndDelegate(Closure closure) {
        def cls = new CacheListenerSupport()
        def owner = closure.owner
        def thisObject = closure.thisObject
        def hydrated = closure.rehydrate(cls, owner, thisObject)
        hydrated.resolveStrategy = DELEGATE_FIRST
        hydrated
    }

}
