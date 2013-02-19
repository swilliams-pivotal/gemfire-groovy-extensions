package com.pivotal.pso.gemfire.util

import com.gemstone.gemfire.cache.execute.FunctionContext

class FunctionContextSupport {

    private final FunctionContext context

    public FunctionContextSupport(FunctionContext context) {
        this.context = context
    }

    def send(Object result) {
        println "FunctionContextSupport.send(${result}) ${this.class}"
        context?.getResultSender().sendResult(result)
    }

    def last(Object result) {
        println "FunctionContextSupport.last(${result}) ${this.class}"
        context?.getResultSender().lastResult(result)
    }

    def error(Throwable exception) {
        println "FunctionContextSupport.error(${exception}) ${this.class}"
        context?.getResultSender().sendException(exception)
    }

}
