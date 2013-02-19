package com.pivotal.pso.gemfire.util

import groovy.transform.CompileStatic

import com.gemstone.gemfire.cache.CacheWriterException


@CompileStatic
class CacheWriterSupport extends CacheListenerSupport {

    def drop(String message = null) {
        println "CacheWriterSupport.drop(${message})"
        if (message == null) {
            message = "Unspecified drop() called"
        }
        throw new CacheWriterException(message)
    }

}
