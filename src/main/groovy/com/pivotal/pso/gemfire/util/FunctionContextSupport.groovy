package com.pivotal.pso.gemfire.util

import com.gemstone.gemfire.cache.execute.FunctionContext

class FunctionContextSupport {

    private final FunctionContext context

    public FunctionContextSupport(FunctionContext context) {
        this.context = context
    }

    def send(Object result) {
        context?.getResultSender().sendResult(result)
    }

    def last(Object result) {
        context?.getResultSender().lastResult(result)
    }

    def error(Throwable exception) {
        context?.getResultSender().sendException(exception)
    }

}
