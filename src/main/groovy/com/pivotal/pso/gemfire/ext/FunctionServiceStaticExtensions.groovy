package com.pivotal.pso.gemfire.ext

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

import com.gemstone.gemfire.cache.Cache
import com.gemstone.gemfire.cache.CacheFactory
import com.gemstone.gemfire.cache.Region
import com.gemstone.gemfire.cache.execute.FunctionAdapter
import com.gemstone.gemfire.cache.execute.FunctionService
import com.pivotal.pso.gemfire.util.ClosureFunctionAdapter
import com.pivotal.pso.gemfire.util.ClosureSerializer


@CompileStatic
class FunctionServiceStaticExtensions {

    static void registerFunction(com.gemstone.gemfire.cache.execute.FunctionService selfType, java.lang.String id, groovy.lang.Closure closure) {

        String name = closure.class.name

        Cache cache = CacheFactory.getAnyInstance()
        Region<String, byte[]> region = cache.getRegion('closure.classloader')
        if (region.containsKey(name)) {
            throw new IllegalArgumentException("Closure Function ${name} already registered")
        }

        byte[] data = new ClosureSerializer().serialize(closure)
        region.put(name, data)

        FunctionAdapter adapter = new ClosureFunctionAdapter(id, true, true, false, closure)
        FunctionService.registerFunction(adapter)

    }

    static void registerFunction(FunctionService selfType, String id, boolean ha, boolean result, boolean optimizeForWrite, Closure closure) {

        String name = closure.class.name

        Cache cache = CacheFactory.getAnyInstance()
        Region<String, byte[]> region = cache.getRegion('closure.classloader')
        if (region.containsKey(name)) {
            throw new IllegalArgumentException("Closure Function ${name} already registered")
        }

        byte[] data = new ClosureSerializer().serialize(closure)
        region.put(name, data)

        FunctionAdapter adapter = new ClosureFunctionAdapter(id, ha, result, optimizeForWrite, closure)
        FunctionService.registerFunction(adapter)

    }

}
