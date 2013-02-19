package com.pivotal.pso.gemfire.util


class ClosureSerializer {

    public byte[] serialize(groovy.lang.Closure closure) {

        String dehydrated = closure.dehydrate().class.name
        println "deyhydrated: " + dehydrated

        String className = closure.class.name
        println "className: " + className

        def loader = Thread.currentThread().contextClassLoader
        InputStream is = loader.getResourceAsStream(className.replace('.', '/') + '.class')
        is.getBytes()
    }

}
