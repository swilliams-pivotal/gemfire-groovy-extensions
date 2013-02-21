package com.pivotal.pso.gemfire.ext

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

import spock.lang.Specification

import com.gemstone.gemfire.cache.Cache
import com.gemstone.gemfire.cache.CacheFactory
import com.gemstone.gemfire.cache.RegionShortcut
import com.gemstone.gemfire.cache.execute.FunctionService


class ClosureFunctionSpec extends Specification {

    Cache cache

    void cleanup() {
        cache?.close()
    }

    def 'Configure closure function'() {
        when:
        cache = new CacheFactory().create()

        def region = cache
            .createRegionFactory(RegionShortcut.REPLICATE)
            .create('regionName')

        assert region != null

        def counter = new AtomicInteger(0)
        FunctionService.registerFunction('myfunc') { fc->
            def hello = fc.getArguments()
            counter.incrementAndGet()
            last hello + ' world'
        }

        region['foo'] = 'bar'

        def results = FunctionService.onRegion(region)
            .withArgs('hello')
            .execute('myfunc')
            .getResult(5, TimeUnit.SECONDS)

        then:
        counter.intValue() == 1
        and:
        'hello world' == results[0]
    }

    def 'Configure closure function error'() {
        when:
        cache = new CacheFactory().create()

        def region = cache
            .createRegionFactory(RegionShortcut.REPLICATE)
            .create('regionName')

        assert region != null

        def counter = new AtomicInteger(0)
        FunctionService.registerFunction('myfunc') { fc->
            counter.incrementAndGet()
            throw new RuntimeException("eep!")
        }

        region['foo'] = 'bar'

        def results = FunctionService.onRegion(region)
            .withArgs('hello')
            .execute('myfunc')
            .getResult(5, TimeUnit.SECONDS)

        then:
        counter.intValue() == 1
        and:
        results[0].class == RuntimeException
    }

}
