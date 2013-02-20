package com.pivotal.pso.gemfire.ext

import java.util.concurrent.atomic.AtomicInteger

import spock.lang.Specification

import com.gemstone.gemfire.cache.Cache
import com.gemstone.gemfire.cache.CacheFactory
import com.gemstone.gemfire.cache.RegionShortcut


class ClosureListenerSpec extends Specification {

    Cache cache

    void cleanup() {
        cache?.close()
    }

    def 'Configure listener with overridden leftShift operator'() {
        when:
        cache = new CacheFactory().create()

        def region = cache
            .createRegionFactory(RegionShortcut.REPLICATE)
            .create('regionName')

        assert region != null

        def counter = new AtomicInteger(0)
        region << {
            afterCreate { e ->
                println "afterCreate: ${e}"
                counter.incrementAndGet()
            }
        }

        region.put('foo', 'bar')

        then:
        counter.intValue() == 1
    }

    def 'Configure writer with overridden rightShift operator'() {
        when:
        cache = new CacheFactory().create()

        def region = cache
            .createRegionFactory(RegionShortcut.REPLICATE)
            .create('regionName')

        assert region != null

        def counter = new AtomicInteger(0)
        region >> {
            beforeCreate { e ->
                println "beforeCreate: ${e}"
                counter.incrementAndGet()
            }
        }

        region.put('foo', 'bar')

        then:
        counter.intValue() == 1
    }

}
