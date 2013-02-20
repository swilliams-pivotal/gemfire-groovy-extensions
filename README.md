gemfire-groovy-extensions
===========

# A Groovy DSL for GemFire

Groovy's Closure object can be used in place of each of GemFire's listener objects.

The meta-programming capabitilities offered by Groovy's extension modules feature is the mechanism that Groovy uses to add methods to existing Java objects.
That functionality is available to the user, so for example, it's possible to add a new method to the Region object that takes a Closure and maps that to the methods in a CacheListener.

    public void cacheListener(Closure closure)

The resulting code in a Groovy program could look like this:

    def region = cache.getRegion('regionName')
    region.cacheListener {
      afterCreate { e->
        println "received afterCreate event: ${e}"
      }
    }

The equivalent code in Java:

    public class MyCacheListener extends CacheListenerAdapter {
      @Override
      public void afterCreate(EntryEvent e) {
        System.out.printf("received beforeCreate event: %s %n", e);
      }
    }

    Region<K,V> region = cache.getRegion('regionName');
    CacheListener cl = new MyCacheListener();
    region.getAttributesMutator().addCacheListener(cl);

Just as easily, a CacheWriter can be implemented using a Closure, with the result as follows:

    region.cacheWriter {
      beforeCreate { e->
        println "received beforeCreate event: ${e}"
      }
    }

The FunctionService Java class can be extended with additional methods too, and then it's possible to write the following:

    def function1 = { fc->
      println "called function! ${fc.getFunctionId()}"
    }

    FunctionService.registerFunction('function1', function1)

Or alternatively and even more concisely:

    FunctionService.registerFunction('function1') { fc->
      println "called function! ${fc.getFunctionId()}"
    }

The newly added method signature is:

    public static void registerFunction(String id, Closure closure)

The closure that is passed in as a parameter is wrapped in a FunctionAdapter that delegates to the closure, passing the FunctionContext value as a parameter when it's executed.
It also modifies the closure's behaviour by adding additional utility methods in its delegate object.

    FunctionService.registerFunction('function1') { fc->
      send('foo')    // equates to fc.getResultSender().sendResult('foo')
      send 'foo'     // these are equivalents in Groovy

      last 'bar'     // equates to fc.getResultSender().lastResult('bar')
    }

The `ClosureFunctionAdapter` class also provides automatic exception handling, so anything thrown from the closure is correctly handled.


Future work will include the ability to serialize a closure and distribute it through GemFire at runtime.


