```
println(numbers.associate { "key$it" to "value$it" })
println(numbers.associateBy { "Key$it" })
println(numbers.associateWith { "Value$it" })
```

```
val pair = numbers.map { number ->
    val complement = complements[number]
    if (complement != null)
        Pair(number, complement)
    else null
}.filterNotNull()
```

```
val pair = numbers.mapNotNull { number ->
        val complement = complements[number]
        if (complement != null)
            Pair(number, complement)
        else null
    }
```

# let

The context object is available as an argument (it). The return value is the lambda result.

# Extensions

Kotlin provides the ability to extend a class or an interface with new functionality without having to inherit from the
class or use design patterns such as Decorator. This is done via special declarations called extensions.

For example, you can write new functions for a class or an interface from a third-party library that you can't modify.
Such functions can be called in the usual way, as if they were methods of the original class. This mechanism is called
an extension function. There are also extension properties that let you define new properties for existing classes.

## Extensions are resolved statically

Extensions do not actually modify the classes they extend. By defining an extension, you are not inserting new members
into a class, only making new functions callable with the dot-notation on variables of this type.

Extension functions are dispatched statically, which means they are not virtual by receiver type. An extension function
being called is determined by the type of the expression on which the function is invoked, not by the type of the result
from evaluating that expression at runtime.

## Nullable receiver

Note that extensions can be defined with a nullable receiver type. These extensions can be called on an object variable
even if its value is null, and they can check for this == null inside the body.

```
fun Any?.toString(): String {
    if (this == null) return "null"
    // after the null check, 'this' is autocast to a non-null type, so the toString() below
    // resolves to the member function of the Any class
    return toString()
}
```

## Extension properties

Kotlin supports extension properties much like it supports functions:

```
val <T> List<T>.lastIndex: Int
    get() = size - 1
```