gemfire-groovy-extensions
===========

A Groovy DSL for GemFire

Using Groovy extensions it's possible to extend the FunctionService Java class with additional methods and thus write the following:

    def function1 = { fc->
      println "called function! ${fc.getFunctionId()}"
    }

    FunctionService.registerFunction('function1', function1)

Or alternatively and even more concisely:

    FunctionService.registerFunction('function1') { fc->
      println "called function! ${fc.getFunctionId()}"
    }

The closure that is passed in as a parameter is wrapped in a FunctionAdapter that delegates to the closure, and modifies its behaviour by adding additional utility methods in the closure's delegate object.

    FunctionService.registerFunction('function1') { fc->
      send('foo')    // equates to fc.getResultSender().sendResult('foo')
      send 'foo'     // these are equivalents in Groovy

      last 'bar'     // equates to fc.getResultSender().lastResult('bar')
    }

The `ClosureFunctionAdapter` class also provides automatic exception handling, so anything thrown from the closure is correctly handled.


Future work will include the ability to serialize a closure and distribute it through GemFire at runtime, using a Region and some interaction with the classloader.


