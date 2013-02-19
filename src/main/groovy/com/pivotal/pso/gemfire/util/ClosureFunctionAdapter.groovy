package com.pivotal.pso.gemfire.util

import static groovy.lang.Closure.DELEGATE_FIRST
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

import com.gemstone.gemfire.cache.execute.FunctionAdapter
import com.gemstone.gemfire.cache.execute.FunctionContext

@CompileStatic
@TypeChecked
class ClosureFunctionAdapter extends FunctionAdapter {

    private final String id

    private final boolean ha

    private final boolean result

    private final boolean optimizeForWrite

    private final Closure<Object> closure

    public ClosureFunctionAdapter(java.lang.String id, boolean ha, boolean result, boolean optimizeForWrite, @DelegatesTo(strategy=DELEGATE_FIRST, value=FunctionContextSupport) groovy.lang.Closure closure) {
        this.id = id
        this.ha = ha
        this.result = result
        this.optimizeForWrite = optimizeForWrite
        this.closure = closure
    }

    public ClosureFunctionAdapter(java.lang.String id, @DelegatesTo(strategy=DELEGATE_FIRST, value=FunctionContextSupport) groovy.lang.Closure closure) {
        this(id, true, true, false, closure)
    }

    @Override
    public void execute(FunctionContext context) {
        def delegate = new FunctionContextSupport(context)
        def hydrated = closure.rehydrate(delegate, this, this)
        hydrated.resolveStrategy = DELEGATE_FIRST

        try {
            hydrated.call(context)
        }
        catch (Throwable t) {
            delegate.error(t)
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean hasResult() {
        return result
    }

    @Override
    public boolean isHA() {
        return ha
    }

    @Override
    public boolean optimizeForWrite() {
        return optimizeForWrite
    }

}
