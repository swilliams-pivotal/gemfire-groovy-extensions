package com.pivotal.pso.gemfire.ext

import java.util.concurrent.atomic.AtomicInteger

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import com.gemstone.gemfire.cache.Cache
import com.gemstone.gemfire.cache.CacheFactory
import com.gemstone.gemfire.cache.RegionShortcut


class ClosureDelegateListenerTest {

    Cache cache

    @Before
    void setup() {
        cache = new CacheFactory().create()
    }

    @After
    void cleanup() {
        cache?.close()
    }

    @Test
    public void testRightShiftWriterDelegateSupportMethod() {

        def region = cache
            .createRegionFactory(RegionShortcut.REPLICATE)
            .create('regionName')

        assert region != null

        def counter = new AtomicInteger(0)
        region.cacheWriter (
            beforeCreate: { e ->
                // FIXME drop("dropped!")
                println "drop.beforeCreate: ${e}" + counter.incrementAndGet()
            }
        )

        region['foo'] = 'bar'

        Assert.assertEquals(1, counter.intValue())
        // FIXME Assert.assertEquals(0, region.size())
    }

}
