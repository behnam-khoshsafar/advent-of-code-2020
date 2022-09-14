All exception classes in Kotlin inherit the Throwable class. Every exception has a message, a stack trace, and an
optional cause.

## Try is an expression

try is an expression, which means it can have a return value:

```val a: Int? = try { input.toInt() } catch (e: NumberFormatException) { null }
```

The returned value of a try expression is either the last expression in the try block or the last expression in the
catch block (or blocks). The contents of the finally block don't affect the result of the expression.

Kotlin does not have checked exceptions.

## The Nothing type

throw is an expression in Kotlin, so you can use it, for example, as part of an Elvis expression:

```
val s = person.name ?: throw IllegalArgumentException("Name required")
```

The throw expression has the type Nothing. This type has no values and is used to mark code locations that can never be
reached. In your own code, you can use Nothing to mark a function that never returns:

```
fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}
```

You may also encounter this type when dealing with type inference. The nullable variant of this type, Nothing?, has
exactly one possible value, which is null. If you use null to initialize a value of an inferred type and there's no
other information that can be used to determine a more specific type, the compiler will infer the Nothing? type:

```
val x = null           // 'x' has type `Nothing?`
val l = listOf(null)   // 'l' has type `List<Nothing?>
```