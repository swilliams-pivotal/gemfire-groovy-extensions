package com.pivotal.pso.gemfire.util

import groovy.transform.CompileStatic

import com.gemstone.gemfire.cache.CacheFactory
import com.gemstone.gemfire.cache.Region
import com.gemstone.gemfire.cache.execute.FunctionService


@CompileStatic
class CacheListenerSupport {

    def send(Object args) {
        println "CacheListenerSupport.send(${args})"
        this
    }

//    def to(String regionName) {
//        println "CacheListenerSupport.to(${regionName})"
//        CacheFactory.getAnyInstance().getRegion(regionName)
//    }

    def func(String func, Object... args) {
        println "CacheListenerSupport.func(${func})"
        Region region = null
        FunctionService.onRegion(region)
    }

    def propertyMissing(String name) {
        if ('cache'.equals(name)) {
            return CacheFactory.getAnyInstance()
        }

        Region region = CacheFactory.getAnyInstance().getRegion(name)
        if (region != null) {
            return region
        }

        println "CacheListenerSupport.propertyMissing.$name"
    }

    def propertyMissing(String name, Object args) {
        println "CacheListenerSupport.propertyMissing.$name = ${args.class}"
    }

    def methodMissing(String name, Object args) {
        println "CacheListenerSupport.methodMissing.$name(${args.class})"
    }

}
