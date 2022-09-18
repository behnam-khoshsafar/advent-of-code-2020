## Delegated properties

With some common kinds of properties, even though you can implement them manually every time you need them, it is more
helpful to implement them once, add them to a library, and reuse them later. For example:

* Lazy properties: the value is computed only on first access.
* Observable properties: listeners are notified about changes to this property.
* Storing properties in a map instead of a separate field for each property.

```
class Example {
    var p: String by Delegate()
}
```

The syntax is: `val/var <property name>: <Type> by <expression>`. The expression after by is a delegate, because the
`get()` (and `set()`) that correspond to the property will be delegated to its `getValue()` and `setValue()` methods.
Property delegates don't have to implement an interface, but they have to provide a `getValue()` function (
and `setValue()` for vars).

```
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}
```

When you read from p, which delegates to an instance of Delegate, the getValue() function from Delegate is called. Its
first parameter is the object you read p from, and the second parameter holds a description of p itself (for example,
you can take its name).

## Standard delegates

### Lazy properties

lazy() is a function that takes a lambda and returns an instance of Lazy<T>, which can serve as a delegate for
implementing a lazy property. The first call to get() executes the lambda passed to lazy() and remembers the result.
Subsequent calls to get() simply return the remembered result.

```
val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

fun main() {
    println(lazyValue)
    println(lazyValue)
}
```

By default, the evaluation of lazy properties is synchronized: the value is computed only in one thread, but all threads
will see the same value. If the synchronization of the initialization delegate is not required to allow multiple threads
to execute it simultaneously, pass LazyThreadSafetyMode.PUBLICATION as a parameter to lazy().

If you're sure that the initialization will always happen in the same thread as the one where you use the property, you
can use LazyThreadSafetyMode.NONE. It doesn't incur any thread-safety guarantees and related overhead.

### Observable properties

Delegates.observable() takes two arguments: the initial value and a handler for modifications. The handler is called
every time you assign to the property (after the assignment has been performed). It has three parameters: the property
being assigned to, the old value, and the new value:

```
import kotlin.properties.Delegates

class User {
    var name: String by Delegates.observable("<no name>") {
        prop, old, new ->
        println("$old -> $new")
    }
}

fun main() {
    val user = User()
    user.name = "first"
    user.name = "second"
}
```

If you want to intercept assignments and veto them, use vetoable() instead of observable(). The handler passed to
vetoable will be called before the assignment of a new property value.

### Delegating to another property

A property can delegate its getter and setter to another property. Such delegation is available for both top-level and
class properties (member and extension). The delegate property can be:

* A top-level property
* A member or an extension property of the same class
* A member or an extension property of another class

To delegate a property to another property, use the `::` qualifier in the delegate name, for example, `this::delegate`
or
`MyClass::delegate`.

```
var topLevelInt: Int = 0
class ClassWithDelegate(val anotherClassInt: Int)

class MyClass(var memberInt: Int, val anotherClassInstance: ClassWithDelegate) {
    var delegatedToMember: Int by this::memberInt
    var delegatedToTopLevel: Int by ::topLevelInt

    val delegatedToAnotherClass: Int by anotherClassInstance::anotherClassInt
}
var MyClass.extDelegated: Int by ::topLevelInt
```

This may be useful, for example, when you want to rename a property in a backward-compatible way: introduce a new
property, annotate the old one with the @Deprecated annotation, and delegate its implementation.

```
class MyClass {
   var newName: Int = 0
   @Deprecated("Use 'newName' instead", ReplaceWith("newName"))
   var oldName: Int by this::newName
}
fun main() {
   val myClass = MyClass()
   // Notification: 'oldName: Int' is deprecated.
   // Use 'newName' instead
   myClass.oldName = 42
   println(myClass.newName) // 42
}
```

### Storing properties in a map

One common use case is storing the values of properties in a map. This comes up often in applications for things like
parsing JSON or performing other dynamic tasks. In this case, you can use the map instance itself as the delegate for a
delegated property.

```
class User(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}

val user = User(mapOf(
    "name" to "John Doe",
    "age"  to 25
))

Delegated properties take values from this map through string keys, which are associated with the names of properties:

println(user.name) // Prints "John Doe"
println(user.age)  // Prints 25
```

### Local delegated properties

You can declare local variables as delegated properties. For example, you can make a local variable lazy:

```
fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo)

    if (someCondition && memoizedFoo.isValid()) {
        memoizedFoo.doSomething()
    }
}
```

## Property delegate requirements

For a read-only property (val), a delegate should provide an operator function getValue() with the following parameters:

* thisRef must be the same type as, or a supertype of, the property owner (for extension properties, it should be the
  type being extended).
* property must be of type KProperty<*> or its supertype.

getValue() must return the same type as the property (or its subtype).

```
class Resource

class Owner {
    val valResource: Resource by ResourceDelegate()
}

class ResourceDelegate {
    operator fun getValue(thisRef: Owner, property: KProperty<*>): Resource {
        return Resource()
    }
}
```

For a mutable property (var), a delegate has to additionally provide an operator function setValue() with the following
parameters:

* thisRef must be the same type as, or a supertype of, the property owner (for extension properties, it should be the
  type being extended).
* property must be of type KProperty<*> or its supertype.
* value must be of the same type as the property (or its supertype).

```
class Resource

class Owner {
    var varResource: Resource by ResourceDelegate()
}

class ResourceDelegate(private var resource: Resource = Resource()) {
    operator fun getValue(thisRef: Owner, property: KProperty<*>): Resource {
        return resource
    }
    operator fun setValue(thisRef: Owner, property: KProperty<*>, value: Any?) {
        if (value is Resource) {
            resource = value
        }
    }
}
```

getValue() and/or setValue() functions can be provided either as member functions of the delegate class or as extension
functions. The latter is handy when you need to delegate a property to an object that doesn't originally provide these
functions. Both of the functions need to be marked with the operator keyword.

You can create delegates as anonymous objects without creating new classes, by using the interfaces ReadOnlyProperty and
ReadWriteProperty from the Kotlin standard library. They provide the required methods: getValue() is declared in
ReadOnlyProperty; ReadWriteProperty extends it and adds setValue(). This means you can pass a ReadWriteProperty whenever
a ReadOnlyProperty is expected.

```
fun resourceDelegate(): ReadWriteProperty<Any?, Int> =
    object : ReadWriteProperty<Any?, Int> {
        var curValue = 0
        override fun getValue(thisRef: Any?, property: KProperty<*>): Int = curValue
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
            curValue = value
        }
    }

val readOnly: Int by resourceDelegate()  // ReadWriteProperty as val
var readWrite: Int by resourceDelegate()
```

## Translation rules for delegated properties

Under the hood, the Kotlin compiler generates an auxiliary property for every delegated property and then delegates to
it. For example, for the property prop it generates the hidden property prop$delegate, and the code of the accessors
simply delegates to this additional property:

```
class C {
    var prop: Type by MyDelegate()
}

// this code is generated by the compiler instead:
class C {
    private val prop$delegate = MyDelegate()
    var prop: Type
        get() = prop$delegate.getValue(this, this::prop)
        set(value: Type) = prop$delegate.setValue(this, this::prop, value)
}
```

### Translation rules when delegating to another property

When delegating to another property, the Kotlin compiler generates immediate access to the referenced property. This
means that the compiler doesn't generate the field prop$delegate. This optimization helps save memory.

```
class C<Type> {
    private var impl: Type = ...
    var prop: Type by ::impl
}

For the code above, the compiler generates the following code:

class C<Type> {
    private var impl: Type = ...

    var prop: Type
        get() = impl
        set(value) {
            impl = value
        }

    fun getProp$delegate(): Type = impl // This method is needed only for reflection
}
```