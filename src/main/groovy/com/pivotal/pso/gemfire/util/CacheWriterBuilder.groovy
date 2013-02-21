package com.pivotal.pso.gemfire.util

import static groovy.lang.Closure.DELEGATE_FIRST
import groovy.transform.CompileStatic

import com.gemstone.gemfire.cache.Region
import com.gemstone.gemfire.cache.util.CacheWriterAdapter


@CompileStatic
class CacheWriterBuilder {

    private final Region region

    CacheWriterBuilder(Region region) {
        this.region = region;
    }

    Map<String, Closure> listener = [:]

    def beforeCreate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeCreate'] = hydrateAndDelegate(closure)
        this
    }

    def beforeUpdate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeUpdate'] = hydrateAndDelegate(closure)
        this
    }

    def beforeDestroy(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeDestroy'] = hydrateAndDelegate(closure)
        this
    }

    def beforeRegionClear(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeRegionClear'] = hydrateAndDelegate(closure)
        this
    }

    def beforeRegionDestroy(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        listener['beforeRegionDestroy'] = hydrateAndDelegate(closure)
        this
    }

    def build() {
        listener as CacheWriterAdapter
    }

    private Closure hydrateAndDelegate(@DelegatesTo(strategy=DELEGATE_FIRST, value=CacheWriterSupport) Closure closure) {
        def cws = new CacheWriterSupport()
        def owner = closure.owner
        def thisObject = closure.thisObject
        def hydrated = closure.rehydrate(cws, region, region)
        hydrated.resolveStrategy = Closure.DELEGATE_ONLY
        hydrated
    }

}
