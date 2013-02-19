package com.pivotal.pso.gemfire.ext

import org.codehaus.groovy.runtime.m12n.SimpleExtensionModule


class GemFireExtensionModules extends SimpleExtensionModule {

    public GemFireExtensionModules() {
        super("gemfire-module", "1.0-SNAPSHOT")
    }

    @Override
    public List<Class> getInstanceMethodsExtensionClasses() {
        return Collections.singletonList(RegionExtensions)
    }

    @Override
    public List<Class> getStaticMethodsExtensionClasses() {
        return Collections.singletonList(FunctionServiceStaticExtensions)
    }

}
